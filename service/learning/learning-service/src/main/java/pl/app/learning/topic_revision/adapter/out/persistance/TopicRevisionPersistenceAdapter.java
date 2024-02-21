package pl.app.learning.topic_revision.adapter.out.persistance;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.event.DelayedDomainEventPublisher;
import pl.app.common.ddd.event.DomainEventPublisherFactory;
import pl.app.learning.topic_revision.application.domain.TopicRevision;
import pl.app.learning.topic_revision.application.domain.TopicRevisionException;
import pl.app.learning.topic_revision.application.port.out.TopicRevisionDomainRepositoryPort;

@Component
@Transactional
@RequiredArgsConstructor
class TopicRevisionPersistenceAdapter implements
        TopicRevisionDomainRepositoryPort {
    private final TopicRevisionRepository repository;
    private final DomainEventPublisherFactory domainEventPublisherFactory;

    @Override
    public TopicRevision load(AggregateId aggregateId) {
        TopicRevision entity = repository.findById(aggregateId)
                .orElseThrow(() -> TopicRevisionException.NotFoundTopicRevisionException.fromId(aggregateId.getId()));
        entity.setEventPublisher(domainEventPublisherFactory.getEventPublisher());
        return entity;
    }

    @Override
    public void save(TopicRevision aggregate) {
        repository.saveAndFlush(aggregate);
        if (aggregate.getEventPublisher() instanceof DelayedDomainEventPublisher publisher) {
            publisher.publishDelayedEvents();
        }
    }

    @Override
    public void delete(AggregateId aggregateId) {
        repository.deleteByAggregateId_AggregateId(aggregateId.getId());
    }
}
