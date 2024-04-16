package pl.app.file.file.application.port.in;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateFileFromBase64Command implements Serializable {
    @NotNull
    private String content;
    @NotNull
    private String fileName;
    private String applicationId;

    public CreateFileFromBase64Command(String content, String fileName) {
        this.content = content;
        this.fileName = fileName;
    }
}
