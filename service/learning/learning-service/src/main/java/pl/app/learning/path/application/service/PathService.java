package pl.app.learning.path.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.cqrs.command.annotation.CommandHandlerAnnotation;
import pl.app.common.cqrs.command.annotation.CommandHandlingAnnotation;
import pl.app.common.ddd.AggregateId;
import pl.app.learning.path.application.domain.Path;
import pl.app.learning.path.application.domain.PathFactory;
import pl.app.learning.path.application.port.in.*;
import pl.app.learning.path.application.port.out.PathDomainRepositoryPort;

import java.util.UUID;

@CommandHandlerAnnotation
@Component
@RequiredArgsConstructor
@Transactional
class PathService implements
        ChangePathStatusUseCase,
        CreatePathUseCase,
        DeletePathUseCase,
        UpdatePathUseCase {
    private final PathFactory factory;
    private final PathDomainRepositoryPort repository;

    @Override
    @CommandHandlingAnnotation
    public void changeStatus(ChangePathStatusCommand command) {
        Path aggregate = repository.load(new AggregateId(command.getPathId()));
        aggregate.changeStatus(command.getStatus());
        repository.save(aggregate);
    }

    @Override
    @CommandHandlingAnnotation
    public UUID create(CreatePathCommand command) {
        Path aggregate = factory.create(command);
        repository.save(aggregate);
        return aggregate.getId();
    }

    @Override
    @CommandHandlingAnnotation
    public void delete(DeletePathCommand command) {
        repository.delete(new AggregateId(command.getPathId()));
    }

    @Override
    @CommandHandlingAnnotation
    public void update(UpdatePathCommand command) {
        Path aggregate = repository.load(new AggregateId(command.getPathId()));
        aggregate.verifyThatPathHaveNoVerifiedStatus();
        // TODO
        repository.save(aggregate);
    }
}
