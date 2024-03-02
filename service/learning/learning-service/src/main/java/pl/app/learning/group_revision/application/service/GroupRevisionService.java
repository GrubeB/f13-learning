package pl.app.learning.group_revision.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.cqrs.command.annotation.CommandHandlerAnnotation;
import pl.app.common.cqrs.command.annotation.CommandHandlingAnnotation;
import pl.app.common.ddd.AggregateId;
import pl.app.learning.category.query.CategoryQueryService;
import pl.app.learning.group.query.GroupQueryService;
import pl.app.learning.group.query.model.GroupQuery;
import pl.app.learning.group_revision.application.domain.GroupHasCategoryRevision;
import pl.app.learning.group_revision.application.domain.GroupHasGroupRevision;
import pl.app.learning.group_revision.application.domain.GroupHasTopicRevision;
import pl.app.learning.group_revision.application.domain.GroupRevision;
import pl.app.learning.group_revision.application.port.in.CreateGroupRevisionUseCase;
import pl.app.learning.group_revision.application.port.in.DeleteGroupRevisionUseCase;
import pl.app.learning.group_revision.application.port.in.UpdateGroupRevisionUseCase;
import pl.app.learning.group_revision.application.port.in.command.CreateGroupRevisionCommand;
import pl.app.learning.group_revision.application.port.in.command.DeleteGroupRevisionCommand;
import pl.app.learning.group_revision.application.port.in.command.UpdateGroupRevisionCommand;
import pl.app.learning.group_revision.application.port.out.GroupRevisionDomainRepositoryPort;
import pl.app.learning.topic.query.TopicQueryService;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Transactional
@CommandHandlerAnnotation
class GroupRevisionService implements
        CreateGroupRevisionUseCase,
        DeleteGroupRevisionUseCase,
        UpdateGroupRevisionUseCase {
    private final GroupRevisionDomainRepositoryPort repository;
    private final GroupQueryService groupQueryService;
    private final CategoryQueryService categoryQueryService;
    private final TopicQueryService topicQueryService;

    @Override
    @CommandHandlingAnnotation
    public UUID create(CreateGroupRevisionCommand command) {
        GroupQuery owner = groupQueryService.fetchById(command.getOwnerId());
        GroupRevision aggregate = new GroupRevision();
        aggregate.setRevisionOwnerId(owner.getId());
        aggregate.setName(command.getName());
        aggregate.setContent(command.getContent());
        aggregate.setStatus(command.getStatus());

        updateCategories(aggregate, command.getCategories());
        updateTopics(aggregate, command.getTopics());
        updateGroups(aggregate, command.getGroups());

        repository.save(aggregate);
        return aggregate.getId();
    }

    private void updateGroups(GroupRevision aggregate, List<CreateGroupRevisionCommand.Group> groupCommands) {
        Map<UUID, AggregateId> groups = groupQueryService.fetchByIds(groupCommands.stream()
                        .map(command -> command.getGroupId())
                        .collect(Collectors.toList()), AggregateId.class)
                .stream().collect(Collectors.toMap(AggregateId::getId, Function.identity()));
        Set<GroupHasGroupRevision> groupHasCategoryRevisions = groupCommands.stream().map(command -> {
            var groupHasGroupRevision = new GroupHasGroupRevision(aggregate, groups.get(command.getGroupId()));
            groupHasGroupRevision.setRevisionOwnerId(command.getOwnerId());
            return groupHasGroupRevision;
        }).collect(Collectors.toSet());
        aggregate.setGroups(groupHasCategoryRevisions);
    }

    private void updateTopics(GroupRevision aggregate, List<CreateGroupRevisionCommand.Topic> topicsCommands) {
        Map<UUID, AggregateId> topics = topicQueryService.fetchByIds(topicsCommands.stream()
                        .map(command -> command.getTopicId())
                        .collect(Collectors.toList()), AggregateId.class)
                .stream().collect(Collectors.toMap(AggregateId::getId, Function.identity()));
        Set<GroupHasTopicRevision> groupHasCategoryRevisions = topicsCommands.stream().map(command -> {
            var groupHasTopicRevision = new GroupHasTopicRevision(aggregate, topics.get(command.getTopicId()));
            groupHasTopicRevision.setRevisionOwnerId(command.getOwnerId());
            return groupHasTopicRevision;
        }).collect(Collectors.toSet());
        aggregate.setTopics(groupHasCategoryRevisions);
    }

    private void updateCategories(GroupRevision aggregate, List<CreateGroupRevisionCommand.Category> categoryCommands) {
        Map<UUID, AggregateId> categories = categoryQueryService.fetchByIds(categoryCommands.stream()
                        .map(command -> command.getCategoryId())
                        .collect(Collectors.toList()), AggregateId.class)
                .stream().collect(Collectors.toMap(AggregateId::getId, Function.identity()));
        Set<GroupHasCategoryRevision> groupHasCategoryRevisions = categoryCommands.stream().map(command -> {
            var groupHasCategoryRevision = new GroupHasCategoryRevision(aggregate, categories.get(command.getCategoryId()));
            groupHasCategoryRevision.setRevisionOwnerId(command.getOwnerId());
            return groupHasCategoryRevision;
        }).collect(Collectors.toSet());
        aggregate.setCategories(groupHasCategoryRevisions);
    }

    @Override
    @CommandHandlingAnnotation
    public void delete(DeleteGroupRevisionCommand command) {
        repository.delete(new AggregateId(command.getGroupRevisionId()));

    }

    @Override
    @CommandHandlingAnnotation
    public void update(UpdateGroupRevisionCommand command) {
        GroupRevision aggregate = repository.load(new AggregateId(command.getRevisionId()));
        aggregate.setName(command.getName());
        aggregate.setContent(command.getContent());
        aggregate.setStatus(command.getStatus());

        updateCategories(aggregate, command.getCategories());
        updateTopics(aggregate, command.getTopics());
        updateGroups(aggregate, command.getGroups());

        repository.save(aggregate);
    }

}
