package pl.app.learning.reference.application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.cqrs.command.annotation.CommandHandlerAnnotation;
import pl.app.common.cqrs.command.annotation.CommandHandlingAnnotation;
import pl.app.common.ddd.AggregateId;
import pl.app.learning.comment.application.port.in.command.CreateCommentContainerCommand;
import pl.app.learning.reference.application.domain.Reference;
import pl.app.learning.reference.application.domain.ReferenceContainer;
import pl.app.learning.reference.application.domain.ReferenceFactory;
import pl.app.learning.reference.application.port.in.CreateReferenceContainerUseCase;
import pl.app.learning.reference.application.port.in.CreateReferenceUseCase;
import pl.app.learning.reference.application.port.in.DeleteReferenceUseCase;
import pl.app.learning.reference.application.port.in.UpdateReferenceUseCase;
import pl.app.learning.reference.application.port.in.command.CreateReferenceCommand;
import pl.app.learning.reference.application.port.in.command.CreateReferenceContainerCommand;
import pl.app.learning.reference.application.port.in.command.DeleteReferenceCommand;
import pl.app.learning.reference.application.port.in.command.UpdateReferenceCommand;
import pl.app.learning.reference.application.port.out.ReferenceContainerDomainRepositoryPort;

import java.util.UUID;

@CommandHandlerAnnotation
@Component
@RequiredArgsConstructor
@Transactional
class ReferenceContainerService implements
        CreateReferenceContainerUseCase {
    private final ReferenceContainerDomainRepositoryPort repository;

    @Override
    @CommandHandlingAnnotation
    public UUID create(CreateReferenceContainerCommand command) {
        var aggregate = new ReferenceContainer(new AggregateId(command.getDomainObjectId()), command.getDomainObjectType());
        repository.save(aggregate);
        return aggregate.getId();
    }

}
