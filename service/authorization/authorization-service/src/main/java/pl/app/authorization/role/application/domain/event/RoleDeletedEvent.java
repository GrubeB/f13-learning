package pl.app.authorization.role.application.domain.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.app.common.cqrs.event.annotation.EventAnnotation;

import java.io.Serializable;
import java.util.UUID;

@EventAnnotation
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDeletedEvent implements Serializable {
    private UUID roleId;
    private String name;
}