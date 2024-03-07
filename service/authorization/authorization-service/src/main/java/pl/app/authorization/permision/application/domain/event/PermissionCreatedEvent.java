package pl.app.authorization.permision.application.domain.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.app.common.cqrs.command.annotation.CommandAnnotation;
import pl.app.common.cqrs.event.annotation.EventAnnotation;

import java.io.Serializable;
import java.util.UUID;

@EventAnnotation
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionCreatedEvent implements Serializable {
    private UUID permissionId;
    private String name;
}