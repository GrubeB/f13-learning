package pl.app.learning.path.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.cqrs.command.gateway.CommandGateway;
import pl.app.common.ddd.AggregateId;
import pl.app.learning.path.application.port.out.CreatePathProgressContainerPort;
import pl.app.learning.progress.application.port.in.CreateProgressContainerCommand;
import pl.app.learning.voting.application.domain.DomainObjectType;

import java.util.UUID;

@Component
@Transactional
@RequiredArgsConstructor
class PathProgressContainerAdapter implements
        CreatePathProgressContainerPort {
    private final CommandGateway gateway;

    @Override
    public AggregateId create(AggregateId aggregateId) {
        UUID progressContainerId = gateway.send(new CreateProgressContainerCommand(aggregateId.getId(), DomainObjectType.PATH));
        return new AggregateId(progressContainerId);
    }
}
