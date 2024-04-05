package pl.app.learning.voting.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.cqrs.command.annotation.CommandHandlerAnnotation;
import pl.app.common.cqrs.command.annotation.CommandHandlingAnnotation;
import pl.app.common.ddd.AggregateId;
import pl.app.learning.voting.application.domain.Voting;
import pl.app.learning.voting.application.port.in.*;
import pl.app.learning.voting.application.port.in.command.*;
import pl.app.learning.voting.application.port.out.VotingDomainRepositoryPort;

import java.util.UUID;

import static org.springframework.transaction.annotation.Isolation.REPEATABLE_READ;

@CommandHandlerAnnotation
@Component
@RequiredArgsConstructor
@Transactional(isolation = REPEATABLE_READ)
class VotingService implements
        CreateVotingUseCase,
        RemoveUserLikeAndDislikeUseCase,
        RemoveUserDislikeUseCase,
        RemoveUserLikeUseCase,
        AddUserDislikeUseCase,
        AddUserLikeUseCase {

    private final VotingDomainRepositoryPort repository;

    @Override
    @CommandHandlingAnnotation
    public UUID create(CreateVotingCommand command) {
        var aggregate = new Voting(new AggregateId(command.getDomainObjectId()), command.getDomainObjectType());
        repository.save(aggregate);
        return aggregate.getId();
    }

    @Override
    @CommandHandlingAnnotation
    public void addDislike(AddUserDislikeCommand command) {
        var aggregate = repository.load(new AggregateId(command.getDomainObjectId()), command.getDomainObjectType());
        aggregate.addDislike(new AggregateId(command.getUserId()));
        repository.save(aggregate);
    }

    @Override
    @CommandHandlingAnnotation
    public void addLike(AddUserLikeCommand command) {
        var aggregate = repository.load(new AggregateId(command.getDomainObjectId()), command.getDomainObjectType());
        aggregate.addLike(new AggregateId(command.getUserId()));
        repository.save(aggregate);
    }

    @Override
    @CommandHandlingAnnotation
    public void removeDislike(RemoveUserDislikeCommand command) {
        var aggregate = repository.load(new AggregateId(command.getDomainObjectId()), command.getDomainObjectType());
        aggregate.removeDislike(new AggregateId(command.getUserId()));
        repository.save(aggregate);
    }

    @Override
    @CommandHandlingAnnotation
    public void removeLike(RemoveUserLikeCommand command) {
        var aggregate = repository.load(new AggregateId(command.getDomainObjectId()), command.getDomainObjectType());
        aggregate.removeLike(new AggregateId(command.getUserId()));
        repository.save(aggregate);
    }

    @Override
    @CommandHandlingAnnotation
    public void removeLikeAndDislike(RemoveUserLikeAndDislikeCommand command) {
        var aggregate = repository.load(new AggregateId(command.getDomainObjectId()), command.getDomainObjectType());
        aggregate.removeLikeAndDislike(new AggregateId(command.getUserId()));
        repository.save(aggregate);
    }
}
