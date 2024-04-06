package pl.app.file.file_storage.application.service;

import lombok.RequiredArgsConstructor;
import pl.app.file.file_storage.application.domain.FileContent;
import pl.app.file.file_storage.application.domain.FileLocation;
import pl.app.file.file_storage.application.domain.FileOperationStatus;
import pl.app.file.file_storage.application.port.in.FileOperationResponse;
import pl.app.file.file_storage.application.port.in.FileStorageService;
import pl.app.file.file_storage.application.port.out.FileStorageRepository;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {
    private final FileStorageRepository repository;

    @Override
    public Optional<FileContent> get(FileLocation fileLocation) {
        try {
            byte[] bytes = repository.get(fileLocation);
            return Optional.of(new FileContent(fileLocation, bytes));
        } catch (Exception exception) {
            return Optional.empty();
        }
    }

    @Override
    public FileOperationResponse save(FileContent fileContent) {
        if (repository.exists(fileContent)) {
            return new FileOperationResponse(FileOperationStatus.FAILED_FILE_ALREADY_EXISTS, "File already exists");
        }
        try {
            repository.save(fileContent);
            return new FileOperationResponse(FileOperationStatus.SUCCESS, "File saved");
        } catch (IOException e) {
            return new FileOperationResponse(FileOperationStatus.FAILED, "Failed to save file");
        }
    }

    @Override
    public FileOperationResponse delete(FileLocation fileLocation) {
        if (!repository.exists(fileLocation)) {
            return new FileOperationResponse(FileOperationStatus.FAILED, "File does not exist");
        }
        try {
            repository.delete(fileLocation);
            return new FileOperationResponse(FileOperationStatus.SUCCESS, "File deleted");
        } catch (IOException e) {
            return new FileOperationResponse(FileOperationStatus.FAILED, "Failed to delete file");
        }


    }

    @Override
    public boolean exists(FileLocation fileLocation) {
        return repository.exists(fileLocation);
    }
}
