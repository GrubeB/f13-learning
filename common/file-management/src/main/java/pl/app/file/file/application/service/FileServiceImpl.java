package pl.app.file.file.application.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.transaction.annotation.Transactional;
import pl.app.file.file.application.domain.File;
import pl.app.file.file.application.domain.FileException;
import pl.app.file.file.application.port.in.CreateFileCommand;
import pl.app.file.file.application.port.in.CreateFileFromBase64Command;
import pl.app.file.file.application.port.in.FileService;
import pl.app.file.file.application.port.out.FileRepository;
import pl.app.file.file_storage.application.domain.FileContent;
import pl.app.file.file_storage.application.domain.FileLocation;
import pl.app.file.file_storage.application.domain.FileOperationStatus;
import pl.app.file.file_storage.application.port.in.FileOperationResponse;
import pl.app.file.file_storage.application.port.in.FileStorageService;
import pl.app.file.shared.CustomByteArrayResource;
import pl.app.file.shared.FileUtils;

import java.util.Base64;
import java.util.UUID;

@Transactional
@Slf4j
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;
    private final FileStorageService fileStorageService;
    private final Environment environment;
    private String applicationId;

    public FileServiceImpl(FileRepository fileRepository, FileStorageService fileStorageService, Environment environment) {
        this.fileRepository = fileRepository;
        this.fileStorageService = fileStorageService;
        this.environment = environment;
        init();
    }

    public void init() {
        try {
            applicationId = environment.getRequiredProperty("spring.application.name");
            log.info("Set '{}' as applicationId.", applicationId);
        } catch (IllegalStateException e) {
            applicationId = "temp";
            log.error("Failed to set applicationId, base on property value associated with key: 'spring.application.name', setting '{}' in result.", applicationId);
        }
    }

    @Override
    public File createFile(CreateFileFromBase64Command command) {
        byte[] byteContent = Base64.getDecoder().decode(command.getContent());
        return createFile(new CreateFileCommand(byteContent, command.getFileName(), resolveApplicationId(command.getApplicationId())));
    }

    @Override
    public File createFile(CreateFileCommand command) {
        final String storageDirectoryName = resolveApplicationId(command.getApplicationId());
        final String storageFileName = FileUtils.getUniqueFileName(command.getFileName());

        File file = File.builder()
                .id(UUID.randomUUID())
                .fileName(command.getFileName())
                .contentType(FileUtils.getExtension(command.getFileName()).orElse(""))
                .size(command.getContent().length)
                .storageFileName(storageFileName)
                .storageDirectoryName(storageDirectoryName)
                .build();

        FileContent fileContent = FileContent.builder()
                .content(command.getContent())
                .name(file.getStorageFileName())
                .directory(file.getStorageDirectoryName())
                .build();
        FileOperationResponse saveOperationResponse = fileStorageService.save(fileContent);
        if (!saveOperationResponse.getStatus().equals(FileOperationStatus.SUCCESS)) {
            throw new RuntimeException(saveOperationResponse.getMessage());
        }

        return fileRepository.save(file);
    }


    @Override
    @Transactional(readOnly = true)
    public File fetchFileById(UUID fileId) {
        return fileRepository.findById(fileId).orElseThrow(() -> FileException.NotFoundFileException.fromId(fileId));
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] fetchFileContentById(UUID fileId) {
        File file = fileRepository.findById(fileId).orElseThrow(() -> FileException.NotFoundFileException.fromId(fileId));
        FileLocation fileLocation = new FileLocation(file.getStorageDirectoryName(), file.getStorageFileName());
        FileContent fileContent = fileStorageService.get(fileLocation)
                .orElseThrow(() -> FileException.NotFoundFileStorageException.fromFileLocation(fileLocation));
        return fileContent.getContent();
    }

    @Override
    @Transactional(readOnly = true)
    public Resource fetchFileAsResourceById(UUID fileId) {
        File file = fileRepository.findById(fileId).orElseThrow(() -> FileException.NotFoundFileException.fromId(fileId));
        FileLocation fileLocation = new FileLocation(file.getStorageDirectoryName(), file.getStorageFileName());
        FileContent fileContent = fileStorageService.get(fileLocation)
                .orElseThrow(() -> FileException.NotFoundFileStorageException.fromFileLocation(fileLocation));
        return new CustomByteArrayResource(fileContent.getContent(), file.getFileName());
    }

    @Override
    public void deleteFileById(UUID fileId) {
        File file = fileRepository.findById(fileId).orElseThrow(() -> FileException.NotFoundFileException.fromId(fileId));
        FileLocation fileLocation = new FileLocation(file.getStorageDirectoryName(), file.getStorageFileName());
        fileStorageService.delete(fileLocation);
        fileRepository.delete(file);
    }

    private String resolveApplicationId(String applicationId) {
        if (applicationId != null && !applicationId.isBlank()) {
            return applicationId;
        }
        return this.applicationId;
    }
}
