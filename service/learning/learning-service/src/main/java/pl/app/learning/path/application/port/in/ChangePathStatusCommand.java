package pl.app.learning.path.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.app.common.cqrs.command.annotation.CommandAnnotation;
import pl.app.learning.path.application.domain.PathStatus;

import java.io.Serializable;
import java.util.UUID;

@CommandAnnotation
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePathStatusCommand implements
        Serializable {
    private UUID pathId;
    private PathStatus status;
}