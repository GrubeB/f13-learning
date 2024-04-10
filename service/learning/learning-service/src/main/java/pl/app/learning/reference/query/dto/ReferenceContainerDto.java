package pl.app.learning.reference.query.dto;

import lombok.Data;
import pl.app.learning.voting.application.domain.DomainObjectType;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Data
public class ReferenceContainerDto implements
        Serializable {
    private Set<ReferenceDto> references;
    private UUID domainObject;
    private DomainObjectType domainObjectType;
}