package pl.app.learning.path.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.cqrs.command.gateway.CommandGateway;
import pl.app.common.ddd.AggregateId;
import pl.app.learning.path.application.port.out.CreatePathVotingPort;
import pl.app.learning.voting.application.domain.DomainObjectType;
import pl.app.learning.voting.application.port.in.command.CreateVotingCommand;

import java.util.UUID;

@Component
@Transactional
@RequiredArgsConstructor
class PathVotingAdapter implements
        CreatePathVotingPort {
    private final CommandGateway gateway;

    @Override
    public AggregateId createVoting(AggregateId aggregateId) {
        UUID votingId = gateway.send(new CreateVotingCommand(aggregateId.getId(), DomainObjectType.PATH));
        return new AggregateId(votingId);
    }
}
