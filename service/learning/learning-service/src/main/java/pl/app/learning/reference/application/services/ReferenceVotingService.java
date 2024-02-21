package pl.app.learning.reference.application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.ddd.AggregateId;
import pl.app.learning.reference.application.domain.Reference;
import pl.app.learning.reference.application.domain.ReferenceFactory;
import pl.app.learning.reference.application.port.in.AddUserDislikeUseCase;
import pl.app.learning.reference.application.port.in.AddUserLikeUseCase;
import pl.app.learning.reference.application.port.in.command.AddUserDislikeCommand;
import pl.app.learning.reference.application.port.in.command.AddUserLikeCommand;
import pl.app.learning.reference.application.port.out.ReferenceDomainRepositoryPort;

@Component
@RequiredArgsConstructor
@Transactional
class ReferenceVotingService implements
        AddUserDislikeUseCase,
        AddUserLikeUseCase {
    private final ReferenceDomainRepositoryPort repository;

    @Override
    public void addDislike(AddUserDislikeCommand command) {
        Reference aggregate = repository.load(new AggregateId(command.getReferenceId()));
        aggregate.addUserDislike(new AggregateId(command.getUserId()));
        repository.save(aggregate);
    }

    @Override
    public void addLike(AddUserLikeCommand command) {
        Reference aggregate = repository.load(new AggregateId(command.getReferenceId()));
        aggregate.addUserLike(new AggregateId(command.getUserId()));
        repository.save(aggregate);
    }
}
