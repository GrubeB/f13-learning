package pl.app.authorization.user.application.port.in.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.app.common.cqrs.command.annotation.CommandAnnotation;

import java.io.Serializable;
import java.util.List;

@CommandAnnotation
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserCommand implements Serializable {
    private String email;
    private String username;
    private String fullName;
    private String password;
    private List<String> roles;
}