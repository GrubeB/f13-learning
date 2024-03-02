package pl.app.learning.topic.application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.cqrs.command.annotation.CommandHandlerAnnotation;
import pl.app.common.cqrs.command.annotation.CommandHandlingAnnotation;
import pl.app.common.ddd.AggregateId;
import pl.app.learning.topic.application.domain.Topic;
import pl.app.learning.topic.application.port.in.MergeRevisionToTopicUseCase;
import pl.app.learning.topic.application.port.in.command.MergeRevisionToTopicCommand;
import pl.app.learning.topic.application.port.out.TopicDomainRepositoryPort;
import pl.app.learning.topic_revision.application.domain.TopicRevision;
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
        TopicRevision topicRevision = topicRevisionQueryService.fetchById(command.getRevisionId());
        aggregate.mergeRevision(topicRevision);
        repositoryPort.save(aggregate);
    }
}
