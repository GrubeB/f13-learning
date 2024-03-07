package pl.app.authorization.permision.application.port.in.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.app.common.cqrs.command.annotation.CommandAnnotation;

import java.io.Serializable;

@CommandAnnotation
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePermissionCommand implements Serializable {
    private String name;
}