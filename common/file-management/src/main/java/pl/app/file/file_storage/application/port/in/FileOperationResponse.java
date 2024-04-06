package pl.app.file.file_storage.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.app.file.file_storage.application.domain.FileOperationStatus;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileOperationResponse implements Serializable {
    private FileOperationStatus status;
    private String message;
}
