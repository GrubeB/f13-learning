package pl.app.file.file.application.port.in;

import org.springframework.core.io.Resource;
import pl.app.file.file.application.domain.File;

import java.util.UUID;

public interface FileService {
    File createFile(CreateFileCommand command);

    File createFile(CreateFileFromBase64Command command);

    void deleteFileById(UUID fileId);

    File fetchFileById(UUID fileId);

    byte[] fetchFileContentById(UUID fileId);

    Resource fetchFileAsResourceById(UUID fileId);

}
