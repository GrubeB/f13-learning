package pl.app.file.file_storage.adapter.out;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import pl.app.file.file_storage.application.domain.FileContent;
import pl.app.file.file_storage.application.domain.FileLocation;
import pl.app.file.file_storage.application.port.out.FileStorageRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

@Slf4j
public class LocalStorageRepository implements FileStorageRepository {
    private final Environment environment;
    private Path fileStorageRootPath;

    public LocalStorageRepository(Environment environment) throws IOException {
        this.environment = environment;
        init();
    }

    public void init() throws IOException {
        try {
            Path path = Path.of(
                    System.getProperty("user.home"),
                    environment.getRequiredProperty("application.storage.local.path"));
            fileStorageRootPath = Files.createDirectories(path);
            log.info("Temporary files location '{}'", fileStorageRootPath.toString());
        } catch (IOException e) {
            log.error("Failed to create local directory to store files");
            throw e;
        }
    }

    @Override
    public byte[] get(FileLocation fileLocation) throws IOException {
        final Path path = pathOfFile(fileLocation);
        return Files.readAllBytes(path);
    }

    @Override
    public void save(FileContent fileContent) throws IOException {
        final Path path = pathOfFile(fileContent);
        createDirectoryIfDoesNotExist(fileContent);
        Files.write(path, fileContent.getContent(), StandardOpenOption.CREATE_NEW);
    }

    private void createDirectoryIfDoesNotExist(FileContent fileContent) throws IOException {
        if (Objects.isNull(fileContent.getDirectory())) return;
        final Path pathOfDirectory = pathOfDirectory(fileContent);
        if (!Files.isDirectory(pathOfDirectory)) {
            Files.createDirectories(pathOfDirectory);
        }
    }

    @Override
    public void delete(FileLocation fileLocation) throws IOException {
        final Path path = pathOfFile(fileLocation);
        Files.delete(path);
    }

    @Override
    public boolean exists(FileLocation fileLocation) {
        final Path path = pathOfFile(fileLocation);
        return Files.exists(path);
    }

    private Path pathOfFile(FileLocation fileLocation) {
        Path relativeFilePath = fileLocation.getDirectory() != null ?
                Path.of(fileLocation.getDirectory(), fileLocation.getName()) :
                Path.of(fileLocation.getName());
        return fileStorageRootPath.resolve(relativeFilePath);
    }

    private Path pathOfDirectory(FileLocation fileLocation) {
        Path relativeFilePath = Path.of(fileLocation.getDirectory());
        return fileStorageRootPath.resolve(relativeFilePath);
    }
}
