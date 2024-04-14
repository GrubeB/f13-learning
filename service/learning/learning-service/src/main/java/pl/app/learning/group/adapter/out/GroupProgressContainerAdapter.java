package pl.app.learning.group.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.cqrs.command.gateway.CommandGateway;
import pl.app.common.ddd.AggregateId;
import pl.app.learning.comment.application.port.in.command.CreateCommentContainerCommand;
import pl.app.learning.group.application.port.out.CreateGroupCommentContainerPort;
import pl.app.learning.group.application.port.out.CreateGroupProgressContainerPort;
import pl.app.learning.progress.application.port.in.CreateProgressContainerCommand;
import pl.app.learning.voting.application.domain.DomainObjectType;

import java.util.UUID;

@Component
@Transactional
@RequiredArgsConstructor
class GroupProgressContainerAdapter implements
        CreateGroupProgressContainerPort {
    private final CommandGateway gateway;

    @Override
    public AggregateId create(AggregateId aggregateId) {
        UUID progressContainerId = gateway.send(new CreateProgressContainerCommand(aggregateId.getId(), DomainObjectType.GROUP));
        return new AggregateId(progressContainerId);
    }
}
