package pl.app.learning.topic_revision.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.cqrs.command.annotation.CommandHandlerAnnotation;
import pl.app.common.cqrs.command.annotation.CommandHandlingAnnotation;
import pl.app.common.ddd.AggregateId;
import pl.app.learning.category.query.CategoryQueryService;
import pl.app.learning.topic.query.TopicQueryService;
import pl.app.learning.topic.query.model.TopicQuery;
import pl.app.learning.topic_revision.application.domain.TopicHasCategoryRevision;
import pl.app.learning.topic_revision.application.domain.TopicRevision;
import pl.app.learning.topic_revision.application.port.in.CreateTopicRevisionUseCase;
import pl.app.learning.topic_revision.application.port.in.DeleteTopicRevisionUseCase;
import pl.app.learning.topic_revision.application.port.in.UpdateTopicRevisionUseCase;
import pl.app.learning.topic_revision.application.port.in.command.CreateTopicRevisionCommand;
import pl.app.learning.topic_revision.application.port.in.command.DeleteTopicRevisionCommand;
import pl.app.learning.topic_revision.application.port.in.command.UpdateTopicRevisionCommand;
import pl.app.learning.topic_revision.application.port.out.TopicRevisionDomainRepositoryPort;

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
class TopicRevisionService implements
        CreateTopicRevisionUseCase,
        DeleteTopicRevisionUseCase,
        UpdateTopicRevisionUseCase {
    private final TopicRevisionDomainRepositoryPort repository;
    private final TopicQueryService topicQueryService;
    private final CategoryQueryService categoryQueryService;

    @Override
    @CommandHandlingAnnotation
    public UUID create(CreateTopicRevisionCommand command) {
        TopicQuery topic = topicQueryService.fetchById(command.getOwnerId());
        TopicRevision aggregate = TopicRevision.builder()
                .revisionOwnerId(topic.getId())
                .name(command.getName())
                .content(command.getContent())
                .build();
        aggregate.setCategories(getTopicHasCategoryRevisions(aggregate, command.getCategories()));
        repository.save(aggregate);
        return aggregate.getId();
    }

    private Set<TopicHasCategoryRevision> getTopicHasCategoryRevisions(TopicRevision aggregate, List<CreateTopicRevisionCommand.Category> categoryCommands) {
        Map<UUID, AggregateId> categories = categoryQueryService.fetchByIds(categoryCommands.stream()
                        .map(command -> command.getCategoryId())
                        .collect(Collectors.toList()), AggregateId.class)
                .stream().collect(Collectors.toMap(AggregateId::getId, Function.identity()));
        return categoryCommands.stream().map(command -> {
            var topicHasCategoryRevision = new TopicHasCategoryRevision(categories.get(command.getCategoryId()), aggregate);
            topicHasCategoryRevision.setRevisionOwnerId(command.getOwnerId());
            return topicHasCategoryRevision;
        }).collect(Collectors.toSet());
    }

    @Override
    @CommandHandlingAnnotation
    public void delete(DeleteTopicRevisionCommand command) {
        repository.delete(new AggregateId(command.getRevisionId()));
    }

    @Override
    @CommandHandlingAnnotation
    public void update(UpdateTopicRevisionCommand command) {
        TopicRevision aggregate = repository.load(new AggregateId(command.getRevisionId()));
        aggregate.setName(command.getName());
        aggregate.setContent(command.getContent());
        aggregate.setCategories(getTopicHasCategoryRevisions(aggregate, command.getCategories()));
        repository.save(aggregate);
    }
}
