package pl.app.learning.topic.application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.cqrs.command.annotation.CommandHandlerAnnotation;
import pl.app.common.cqrs.command.annotation.CommandHandlingAnnotation;
import pl.app.common.ddd.AggregateId;
import pl.app.learning.topic.application.domain.Topic;
import pl.app.learning.topic.application.domain.TopicException;
import pl.app.learning.topic.application.domain.TopicSnapshot;
import pl.app.learning.topic.application.port.in.MergeRevisionToTopicUseCase;
import pl.app.learning.topic.application.port.in.RevertTopicSnapshotUseCase;
import pl.app.learning.topic.application.port.in.command.MergeRevisionToTopicCommand;
import pl.app.learning.topic.application.port.in.command.RevertTopicSnapshotCommand;
import pl.app.learning.topic.application.port.out.TopicDomainRepositoryPort;
import pl.app.learning.topic_revision.query.TopicRevisionQuery;
import pl.app.learning.topic_revision.query.TopicRevisionQueryService;

@CommandHandlerAnnotation
@Component("pl.app.learning.topic.application.services.TopicRevisionService")
@RequiredArgsConstructor
@Transactional
class TopicRevisionService implements
        MergeRevisionToTopicUseCase {
    private final TopicDomainRepositoryPort repositoryPort;
    private final TopicRevisionQueryService topicRevisionQueryService;
    @Override
    @CommandHandlingAnnotation
    public void merge(MergeRevisionToTopicCommand command) {
        Topic aggregate = repositoryPort.load(new AggregateId(command.getTopicId()));
        TopicRevisionQuery topicRevisionQuery = topicRevisionQueryService.fetchById(command.getRevisionId());
        aggregate.mergeRevision(topicRevisionQuery);
        repositoryPort.save(aggregate);
    }
}
