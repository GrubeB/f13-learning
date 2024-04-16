package pl.app.learning.progress.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.app.common.cqrs.command.annotation.CommandAnnotation;
import pl.app.learning.progress.application.domain.ProgressType;
import pl.app.learning.voting.application.domain.DomainObjectType;

import java.io.Serializable;
import java.util.UUID;

@CommandAnnotation
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SetUserProgressCommand implements
        Serializable {
    private UUID domainObjectId;
    private DomainObjectType domainObjectType;
    private UUID userId;
    private ProgressType type;
}