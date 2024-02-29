package pl.app.learning.reference.application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.cqrs.command.annotation.CommandHandlerAnnotation;
import pl.app.common.cqrs.command.annotation.CommandHandlingAnnotation;
import pl.app.common.ddd.AggregateId;
import pl.app.learning.reference.application.domain.Reference;
import pl.app.learning.reference.application.domain.ReferenceFactory;
import pl.app.learning.reference.application.port.in.AddUserDislikeUseCase;
import pl.app.learning.reference.application.port.in.AddUserLikeUseCase;
import pl.app.learning.reference.application.port.in.RemoveUserDislikeUseCase;
import pl.app.learning.reference.application.port.in.RemoveUserLikeUseCase;
import pl.app.learning.reference.application.port.in.command.AddUserDislikeCommand;
import pl.app.learning.reference.application.port.in.command.AddUserLikeCommand;
import pl.app.learning.reference.application.port.in.command.RemoveUserDislikeCommand;
import pl.app.learning.reference.application.port.in.command.RemoveUserLikeCommand;
import pl.app.learning.reference.application.port.out.ReferenceDomainRepositoryPort;
@CommandHandlerAnnotation
@Component
@RequiredArgsConstructor
@Transactional
class ReferenceVotingService implements
        RemoveUserDislikeUseCase,
        RemoveUserLikeUseCase,
        AddUserDislikeUseCase,
        AddUserLikeUseCase {
    private final ReferenceDomainRepositoryPort repository;

    @Override
    @CommandHandlingAnnotation
    public void addDislike(AddUserDislikeCommand command) {
        Reference aggregate = repository.load(new AggregateId(command.getReferenceId()));
        aggregate.addUserDislike(new AggregateId(command.getUserId()));
        repository.save(aggregate);
    }

    @Override
    @CommandHandlingAnnotation
    public void addLike(AddUserLikeCommand command) {
        Reference aggregate = repository.load(new AggregateId(command.getReferenceId()));
        aggregate.addUserLike(new AggregateId(command.getUserId()));
        repository.save(aggregate);
    }

    @Override
    @CommandHandlingAnnotation
    public void removeDislike(RemoveUserDislikeCommand command) {
        Reference aggregate = repository.load(new AggregateId(command.getReferenceId()));
        aggregate.removeDislike(new AggregateId(command.getUserId()));
        repository.save(aggregate);
    }

    @Override
    @CommandHandlingAnnotation
    public void removeLike(RemoveUserLikeCommand command) {
        Reference aggregate = repository.load(new AggregateId(command.getReferenceId()));
        aggregate.removeLike(new AggregateId(command.getUserId()));
        repository.save(aggregate);
    }
}
