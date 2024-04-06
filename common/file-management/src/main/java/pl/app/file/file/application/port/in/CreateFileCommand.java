package pl.app.file.file.application.port.in;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateFileCommand implements Serializable {
    @NotNull
    private byte[] content;
    @NotNull
    private String fileName;
    private String applicationId;

    public CreateFileCommand(@NotNull byte[] content, String fileName) {
        this.content = content;
        this.fileName = fileName;
    }
}
