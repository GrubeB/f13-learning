package pl.app.learning.topic.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.cqrs.command.gateway.CommandGateway;
import pl.app.common.ddd.AggregateId;
import pl.app.learning.progress.application.port.in.CreateProgressContainerCommand;
import pl.app.learning.topic.application.port.out.CreateTopicProgressContainerPort;
import pl.app.learning.voting.application.domain.DomainObjectType;

import java.util.UUID;

@Component
@Transactional
@RequiredArgsConstructor
class TopicProgressContainerAdapter implements
        CreateTopicProgressContainerPort {
    private final CommandGateway gateway;

    @Override
    public AggregateId create(AggregateId aggregateId) {
        UUID progressContainerId = gateway.send(new CreateProgressContainerCommand(aggregateId.getId(), DomainObjectType.TOPIC));
        return new AggregateId(progressContainerId);
    }
}
