package pl.app.file.file_storage.application.port.in;

import pl.app.file.file_storage.application.domain.FileContent;
import pl.app.file.file_storage.application.domain.FileLocation;

import java.util.Optional;


public interface FileStorageService {
    Optional<FileContent> get(FileLocation fileLocation);

    FileOperationResponse save(FileContent fileContent);

    FileOperationResponse delete(FileLocation fileLocation);

    boolean exists(FileLocation fileLocation);
}
