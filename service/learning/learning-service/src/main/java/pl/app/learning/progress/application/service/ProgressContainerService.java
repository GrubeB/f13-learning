package pl.app.learning.progress.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.cqrs.command.annotation.CommandHandlerAnnotation;
import pl.app.common.cqrs.command.annotation.CommandHandlingAnnotation;
import pl.app.common.ddd.AggregateId;
import pl.app.learning.progress.application.domain.ProgressContainer;
import pl.app.learning.progress.application.port.in.CreateProgressContainerCommand;
import pl.app.learning.progress.application.port.in.CreateProgressContainerUseCase;
import pl.app.learning.progress.application.port.in.SetUserProgressCommand;
import pl.app.learning.progress.application.port.in.SetUserProgressUseCase;
import pl.app.learning.progress.application.port.out.ProgressContainerRepositoryPort;

import java.util.UUID;

@CommandHandlerAnnotation
@Component
@RequiredArgsConstructor
@Transactional
class ProgressContainerService implements
        CreateProgressContainerUseCase,
        SetUserProgressUseCase {

    private final ProgressContainerRepositoryPort repository;

    @Override
    @CommandHandlingAnnotation
    public UUID create(CreateProgressContainerCommand command) {
        var aggregate = new ProgressContainer(new AggregateId(command.getDomainObjectId()), command.getDomainObjectType());
        repository.save(aggregate);
        return aggregate.getId();
    }

    @Override
    @CommandHandlingAnnotation
    public void setUserProgress(SetUserProgressCommand command) {
        var aggregate = repository.load(new AggregateId(command.getDomainObjectId()), command.getDomainObjectType());
        aggregate.setUserProgress(command.getUserId(), command.getType());
        repository.save(aggregate);
    }
}
