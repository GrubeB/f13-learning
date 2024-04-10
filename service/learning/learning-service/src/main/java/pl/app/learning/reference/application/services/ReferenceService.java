package pl.app.learning.reference.application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.cqrs.command.annotation.CommandHandlerAnnotation;
import pl.app.common.cqrs.command.annotation.CommandHandlingAnnotation;
import pl.app.common.ddd.AggregateId;
import pl.app.learning.reference.application.domain.Reference;
import pl.app.learning.reference.application.domain.ReferenceContainer;
import pl.app.learning.reference.application.domain.ReferenceFactory;
import pl.app.learning.reference.application.port.in.CreateReferenceUseCase;
import pl.app.learning.reference.application.port.in.DeleteReferenceUseCase;
import pl.app.learning.reference.application.port.in.UpdateReferenceUseCase;
import pl.app.learning.reference.application.port.in.command.CreateReferenceCommand;
import pl.app.learning.reference.application.port.in.command.DeleteReferenceCommand;
import pl.app.learning.reference.application.port.in.command.UpdateReferenceCommand;
import pl.app.learning.reference.application.port.out.ReferenceContainerDomainRepositoryPort;
import pl.app.learning.reference.application.port.out.ReferenceDomainRepositoryPort;

import java.util.UUID;

@CommandHandlerAnnotation
@Component
@RequiredArgsConstructor
@Transactional
class ReferenceService implements
        DeleteReferenceUseCase,
        UpdateReferenceUseCase,
        CreateReferenceUseCase {
    private final ReferenceFactory factory;
    private final ReferenceContainerDomainRepositoryPort repository;

    @Override
    @CommandHandlingAnnotation
    public UUID createReference(CreateReferenceCommand command) {
        var aggregate = repository.load(new AggregateId(command.getDomainObjectId()), command.getDomainObjectType());
        Reference newReference = factory.create(command.getAuthor(), command.getTitle(), command.getPublicationDate(), command.getDescription(), command.getLink());
        aggregate.addReference(newReference);
        repository.save(aggregate);
        return newReference.getId();
    }

    @Override
    @CommandHandlingAnnotation
    public void updateReference(UpdateReferenceCommand command) {
        var aggregate = repository.loadByReference(new AggregateId(command.getReferenceId()));
        aggregate.updateReference(new AggregateId(command.getReferenceId()),command.getAuthor(), command.getTitle(), command.getPublicationDate(), command.getDescription(), command.getLink());
        repository.save(aggregate);
    }

    @Override
    @CommandHandlingAnnotation
    public void deleteReference(DeleteReferenceCommand command) {
        var aggregate = repository.loadByReference(new AggregateId(command.getReferenceId()));
        aggregate.removeReference(new AggregateId(command.getReferenceId()));
        repository.save(aggregate);
    }
}
