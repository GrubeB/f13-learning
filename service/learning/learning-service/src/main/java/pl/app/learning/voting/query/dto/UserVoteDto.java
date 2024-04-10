package pl.app.learning.voting.query.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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