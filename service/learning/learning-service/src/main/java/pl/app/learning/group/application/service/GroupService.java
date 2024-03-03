package pl.app.learning.group.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.cqrs.command.annotation.CommandHandlerAnnotation;
import pl.app.common.cqrs.command.annotation.CommandHandlingAnnotation;
import pl.app.common.ddd.AggregateId;
import pl.app.learning.group.application.domain.Group;
import pl.app.learning.group.application.domain.GroupFactory;
import pl.app.learning.group.application.port.in.ChangeGroupStatusUseCase;
import pl.app.learning.group.application.port.in.CreateGroupUseCase;
import pl.app.learning.group.application.port.in.DeleteGroupUseCase;
import pl.app.learning.group.application.port.in.UpdateGroupUseCase;
import pl.app.learning.group.application.port.in.command.ChangeGroupStatusCommand;
import pl.app.learning.group.application.port.in.command.CreateGroupCommand;
import pl.app.learning.group.application.port.in.command.DeleteGroupCommand;
import pl.app.learning.group.application.port.in.command.UpdateGroupCommand;
import pl.app.learning.group.application.port.out.GroupDomainRepositoryPort;

import java.util.UUID;
import java.util.stream.Collectors;

@CommandHandlerAnnotation
@Component
@RequiredArgsConstructor
@Transactional
class GroupService implements
        ChangeGroupStatusUseCase,
        UpdateGroupUseCase,
        DeleteGroupUseCase,
        CreateGroupUseCase {
    private final GroupFactory factory;
    private final GroupDomainRepositoryPort repository;


    @Override
    @CommandHandlingAnnotation
    public UUID create(CreateGroupCommand command) {
        Group group = factory.create(command.getName(), command.getContent(), command.getCategoryIds(), command.getTopicIds(), command.getGroupIds());
        repository.save(group);
        return group.getId();
    }

    @Override
    @CommandHandlingAnnotation
    public void delete(DeleteGroupCommand command) {
        repository.delete(new AggregateId(command.getGroupId()));
    }

    @Override
    @CommandHandlingAnnotation
    public void update(UpdateGroupCommand command) {
        Group aggregate = repository.load(new AggregateId(command.getGroupId()));
        aggregate.verifyThatTopicHaveNoVerifiedStatus();
        aggregate.updateContent(command.getName(), command.getContent());
        aggregate.setCategories(command.getCategoryIds().stream().map(AggregateId::new).collect(Collectors.toSet()));
        aggregate.setTopics(command.getTopicIds().stream().map(AggregateId::new).collect(Collectors.toSet()));
        aggregate.setGroups(command.getGroupIds().stream().map(AggregateId::new).collect(Collectors.toSet()));
        repository.save(aggregate);
    }

    @Override
    @CommandHandlingAnnotation
    public void changeStatus(ChangeGroupStatusCommand command) {
        Group aggregate = repository.load(new AggregateId(command.getGroupId()));
        aggregate.changeStatus(command.getStatus());
        repository.save(aggregate);
    }
}
