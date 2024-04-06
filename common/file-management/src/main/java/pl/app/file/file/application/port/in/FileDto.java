package pl.app.file.file.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileDto implements Serializable {
    private UUID id;
    private String fileName;
    private String contentType;
    private Integer size;
}
