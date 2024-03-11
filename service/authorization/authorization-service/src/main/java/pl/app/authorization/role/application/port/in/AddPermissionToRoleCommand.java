package pl.app.authorization.role.application.port.in;

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
public class AddPermissionToRoleCommand implements Serializable {
    private String name;
    private List<String> permissions;
}