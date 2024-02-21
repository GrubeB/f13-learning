package pl.app.learning.reference.application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.ddd.AggregateId;
import pl.app.learning.reference.application.domain.Reference;
import pl.app.learning.reference.application.domain.ReferenceFactory;
import pl.app.learning.reference.application.port.in.*;
import pl.app.learning.reference.application.port.in.command.*;
import pl.app.learning.reference.application.port.out.ReferenceDomainRepositoryPort;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Transactional
class ReferenceService implements
        DeleteReferenceUseCase,
        UpdateReferenceUseCase,
        CreateReferenceUseCase {
    private final ReferenceFactory factory;
    private final ReferenceDomainRepositoryPort repository;

    @Override
    public UUID createReference(CreateReferenceCommand command) {
        Reference aggregate = factory.create(command.getAuthor(), command.getTitle(), command.getPublicationDate(), command.getDescription(), command.getLink());
        repository.save(aggregate);
        return aggregate.getId();
    }

    @Override
    public void updateReference(UpdateReferenceCommand command) {
        Reference aggregate = repository.load(new AggregateId(command.getReferenceId()));
        aggregate.updateReferenceInfo(command.getAuthor(), command.getTitle(), command.getPublicationDate(), command.getDescription(), command.getLink());
        repository.save(aggregate);
    }

    @Override
    public void deleteReference(DeleteReferenceCommand command) {
        repository.delete(new AggregateId(command.getReferenceId()));
    }
}
