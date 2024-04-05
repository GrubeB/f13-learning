package pl.app.learning.comment.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.cqrs.command.gateway.CommandGateway;
import pl.app.common.ddd.AggregateId;
import pl.app.learning.comment.application.port.out.CreateCommentVotingPort;
import pl.app.learning.voting.application.domain.DomainObjectType;
import pl.app.learning.voting.application.port.in.command.CreateVotingCommand;

import java.util.UUID;

@Component
@Transactional
@RequiredArgsConstructor
class CommentVotingAdapter implements
        CreateCommentVotingPort {
    private final CommandGateway gateway;

    @Override
    public AggregateId createVoting(AggregateId aggregateId) {
        UUID votingId = gateway.send(new CreateVotingCommand(aggregateId.getId(), DomainObjectType.COMMENT));
        return new AggregateId(votingId);
    }
}
