package pl.app.authorization.auth.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.app.common.cqrs.command.annotation.CommandAnnotation;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationCommandResponse implements Serializable {
    private String accessToken;
    private String tokenType;
    private String expiresIn;
}
