package pl.app.learning.reference.query.dto;

import lombok.Data;
import pl.app.learning.reference.application.domain.ReferenceStatus;
import pl.app.learning.reference.query.model.ReferenceQuery;
import pl.app.learning.voting.application.domain.DomainObjectType;
import pl.app.learning.voting.query.dto.VotingDto;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Data
public class ReferenceContainerDto implements
        Serializable {
    private Set<ReferenceDto> references;
    private UUID domainObject;
    private DomainObjectType domainObjectType;
}