package pl.app.learning.group.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.cqrs.command.annotation.CommandHandlerAnnotation;
import pl.app.common.cqrs.command.annotation.CommandHandlingAnnotation;
import pl.app.common.ddd.AggregateId;
import pl.app.learning.group.application.domain.Group;
import pl.app.learning.group.application.port.in.AddReferenceToGroupUseCase;
import pl.app.learning.group.application.port.in.RemoveReferenceFromGroupUseCase;
import pl.app.learning.group.application.port.in.command.AddReferenceToGroupCommand;
import pl.app.learning.group.application.port.in.command.RemoveReferenceFromGroupCommand;
import pl.app.learning.group.application.port.out.GroupDomainRepositoryPort;

@CommandHandlerAnnotation
@Component
@RequiredArgsConstructor
@Transactional
class GroupReferenceService implements
        AddReferenceToGroupUseCase,
        RemoveReferenceFromGroupUseCase {
    private final GroupDomainRepositoryPort repository;

    @Override
    @CommandHandlingAnnotation
    public void addReference(AddReferenceToGroupCommand command) {
        Group aggregate = repository.load(new AggregateId(command.getGroupId()));
        aggregate.addReference(new AggregateId(command.getReferenceId()));
    }

    @Override
    @CommandHandlingAnnotation
    public void removeReference(RemoveReferenceFromGroupCommand command) {
        Group aggregate = repository.load(new AggregateId(command.getGroupId()));
        aggregate.removeReference(new AggregateId(command.getReferenceId()));
    }
}
