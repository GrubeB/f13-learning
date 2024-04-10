package pl.app.learning.topic.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.cqrs.command.gateway.CommandGateway;
import pl.app.common.ddd.AggregateId;
import pl.app.learning.reference.application.port.in.command.CreateReferenceContainerCommand;
import pl.app.learning.topic.application.port.out.CreateTopicReferenceContainerPort;
import pl.app.learning.voting.application.domain.DomainObjectType;

import java.util.UUID;

@Component
@Transactional
@RequiredArgsConstructor
class TopicReferenceContainerAdapter implements
        CreateTopicReferenceContainerPort {
    private final CommandGateway gateway;

    @Override
    public AggregateId create(AggregateId aggregateId) {
        UUID referenceContainerId = gateway.send(new CreateReferenceContainerCommand(aggregateId.getId(), DomainObjectType.TOPIC));
        return new AggregateId(referenceContainerId);
    }
}
