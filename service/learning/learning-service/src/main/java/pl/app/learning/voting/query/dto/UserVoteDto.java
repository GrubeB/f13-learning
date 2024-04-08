package pl.app.learning.voting.query.dto;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.app.common.ddd.AggregateId;
import pl.app.learning.voting.application.domain.DomainObjectType;
import pl.app.learning.voting.application.domain.UserVoteType;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVoteDto implements
        Serializable {
    private UUID userId;
    private UserVoteType type;
    private UUID domainObject;
    private DomainObjectType domainObjectType;
}