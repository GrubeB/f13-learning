package pl.app.file.file_storage.application.port.out;

import pl.app.file.file_storage.application.domain.FileContent;
import pl.app.file.file_storage.application.domain.FileLocation;

import java.io.IOException;


public interface FileStorageRepository {
    byte[] get(FileLocation fileLocation) throws IOException;

    void save(FileContent fileContent) throws IOException;

    void delete(FileLocation fileLocation) throws IOException;

    boolean exists(FileLocation fileLocation);
}
