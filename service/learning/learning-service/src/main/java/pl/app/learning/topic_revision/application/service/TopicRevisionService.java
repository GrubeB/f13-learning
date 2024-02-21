package pl.app.learning.topic_revision.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.cqrs.command.annotation.CommandHandlerAnnotation;
import pl.app.common.cqrs.command.annotation.CommandHandlingAnnotation;
import pl.app.common.ddd.AggregateId;
import pl.app.learning.topic.query.TopicQuery;
import pl.app.learning.topic.query.TopicQueryService;
import pl.app.learning.topic_revision.application.domain.TopicRevision;
import pl.app.learning.topic_revision.application.port.in.CreateTopicRevisionUseCase;
import pl.app.learning.topic_revision.application.port.in.DeleteTopicRevisionUseCase;
import pl.app.learning.topic_revision.application.port.in.UpdateTopicRevisionUseCase;
import pl.app.learning.topic_revision.application.port.in.command.CreateTopicRevisionCommand;
import pl.app.learning.topic_revision.application.port.in.command.DeleteTopicRevisionCommand;
import pl.app.learning.topic_revision.application.port.in.command.UpdateTopicRevisionCommand;
import pl.app.learning.topic_revision.application.port.out.TopicRevisionDomainRepositoryPort;

import java.util.UUID;

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

    @Override
    @CommandHandlingAnnotation
    public UUID create(CreateTopicRevisionCommand command) {
        TopicQuery topic = topicQueryService.fetchById(command.getTopicId());
        TopicRevision aggregate = new TopicRevision(command.getName(), command.getContent(), topic);
        repository.save(aggregate);
        return aggregate.getId();
    }

    @Override
    @CommandHandlingAnnotation
    public void delete(DeleteTopicRevisionCommand command) {
        repository.delete(new AggregateId(command.getTopicRevisionId()));
    }

    @Override
    @CommandHandlingAnnotation
    public void update(UpdateTopicRevisionCommand command) {
        TopicRevision aggregate = repository.load(new AggregateId(command.getTopicRevisionId()));
        aggregate.setName(command.getName());
        aggregate.setContent(command.getContent());
        repository.save(aggregate);
    }
}
