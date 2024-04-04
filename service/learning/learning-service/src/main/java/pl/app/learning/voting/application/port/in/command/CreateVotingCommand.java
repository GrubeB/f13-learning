package pl.app.learning.voting.application.port.in.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.app.common.cqrs.command.annotation.CommandAnnotation;
import pl.app.learning.voting.application.domain.DomainObjectType;

import java.io.Serializable;
import java.util.UUID;

@CommandAnnotation
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateVotingCommand implements
        Serializable {
    private UUID domainObjectId;
    private DomainObjectType domainObjectType;
}