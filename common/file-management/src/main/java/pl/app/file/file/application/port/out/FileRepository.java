package pl.app.file.file.application.port.out;

import pl.app.file.file.application.domain.File;

import java.util.Optional;
import java.util.UUID;

public interface FileRepository {
    File save(File entity);

    Optional<File> findById(UUID fileId);

    void delete(File entity);
}
