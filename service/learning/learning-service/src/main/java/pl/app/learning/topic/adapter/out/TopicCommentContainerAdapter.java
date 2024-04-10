package pl.app.learning.topic.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.cqrs.command.gateway.CommandGateway;
import pl.app.common.ddd.AggregateId;
import pl.app.learning.comment.application.port.in.command.CreateCommentContainerCommand;
import pl.app.learning.topic.application.port.out.CreateTopicCommentContainerPort;
import pl.app.learning.voting.application.domain.DomainObjectType;

import java.util.UUID;

@Component
@Transactional
@RequiredArgsConstructor
class TopicCommentContainerAdapter implements
        CreateTopicCommentContainerPort {
    private final CommandGateway gateway;

    @Override
    public AggregateId create(AggregateId aggregateId) {
        UUID commandContainerId = gateway.send(new CreateCommentContainerCommand(aggregateId.getId(), DomainObjectType.TOPIC));
        return new AggregateId(commandContainerId);
    }
}
