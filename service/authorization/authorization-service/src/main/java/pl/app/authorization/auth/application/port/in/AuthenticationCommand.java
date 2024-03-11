package pl.app.authorization.auth.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.app.common.cqrs.command.annotation.CommandAnnotation;

import java.io.Serializable;

@CommandAnnotation
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationCommand implements Serializable {
    private String email;
    private String password;
}
