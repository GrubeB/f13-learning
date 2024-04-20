package pl.app.learning.path.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.cqrs.command.gateway.CommandGateway;
import pl.app.common.ddd.AggregateId;
import pl.app.learning.comment.application.port.in.command.CreateCommentContainerCommand;
import pl.app.learning.path.application.port.out.CreatePathCommentContainerPort;
import pl.app.learning.voting.application.domain.DomainObjectType;

import java.util.UUID;

@Component
@Transactional
@RequiredArgsConstructor
class PathCommentContainerAdapter implements
        CreatePathCommentContainerPort {
    private final CommandGateway gateway;

    @Override
    public AggregateId create(AggregateId aggregateId) {
        UUID commandContainerId = gateway.send(new CreateCommentContainerCommand(aggregateId.getId(), DomainObjectType.PATH));
        return new AggregateId(commandContainerId);
    }
}
