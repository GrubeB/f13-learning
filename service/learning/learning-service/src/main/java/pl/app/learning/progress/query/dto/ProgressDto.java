package pl.app.learning.progress.query.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.app.learning.progress.application.domain.ProgressType;
import pl.app.learning.voting.application.domain.DomainObjectType;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgressDto implements
        Serializable {
    private UUID id;
    private UUID userId;
    private ProgressType type;
    private UUID domainObject;
    private DomainObjectType domainObjectType;
}