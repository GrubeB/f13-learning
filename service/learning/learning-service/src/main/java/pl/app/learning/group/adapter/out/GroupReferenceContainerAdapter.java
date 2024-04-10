package pl.app.learning.group.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.cqrs.command.gateway.CommandGateway;
import pl.app.common.ddd.AggregateId;
import pl.app.learning.group.application.port.out.CreateGroupReferenceContainerPort;
import pl.app.learning.reference.application.port.in.command.CreateReferenceContainerCommand;
import pl.app.learning.topic.application.port.out.CreateTopicReferenceContainerPort;
import pl.app.learning.voting.application.domain.DomainObjectType;

import java.util.UUID;

@Component
@Transactional
@RequiredArgsConstructor
class GroupReferenceContainerAdapter implements
        CreateGroupReferenceContainerPort {
    private final CommandGateway gateway;

    @Override
    public AggregateId create(AggregateId aggregateId) {
        UUID referenceContainerId = gateway.send(new CreateReferenceContainerCommand(aggregateId.getId(), DomainObjectType.GROUP));
        return new AggregateId(referenceContainerId);
    }
}
