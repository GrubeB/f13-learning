package pl.app.learning.group_revision.application.port.in.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.app.common.cqrs.command.annotation.CommandAnnotation;
import pl.app.learning.group.application.domain.GroupStatus;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@CommandAnnotation
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteGroupRevisionCommand implements
        Serializable {
    private UUID groupRevisionId;
}