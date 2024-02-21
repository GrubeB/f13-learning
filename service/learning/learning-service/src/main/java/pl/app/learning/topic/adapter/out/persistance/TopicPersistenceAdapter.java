package pl.app.learning.topic.adapter.out.persistance;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.event.DelayedDomainEventPublisher;
import pl.app.common.ddd.event.DomainEventPublisherFactory;
import pl.app.learning.topic.application.domain.Topic;
import pl.app.learning.topic.application.domain.TopicException;
import pl.app.learning.topic.application.port.out.TopicDomainRepositoryPort;

@Component
@Transactional
@RequiredArgsConstructor
class TopicPersistenceAdapter implements
        TopicDomainRepositoryPort {
    private final TopicRepository repository;
    private final TopicSnapshotRepository topicSnapshotRepository;
    private final DomainEventPublisherFactory domainEventPublisherFactory;

    @Override
    public Topic load(AggregateId aggregateId) {
        Topic entity = repository.findById(aggregateId)
                .orElseThrow(() -> TopicException.NotFoundTopicException.fromId(aggregateId.getId()));
        entity.setEventPublisher(domainEventPublisherFactory.getEventPublisher());
        entity.makeAndStoreSnapshot();
        return entity;
    }

    @Override
    public void save(Topic aggregate) {
        repository.saveAndFlush(aggregate);
        topicSnapshotRepository.saveAll(aggregate.getTransientSnapshots());
        if (aggregate.getEventPublisher() instanceof DelayedDomainEventPublisher publisher) {
            publisher.publishDelayedEvents();
        }
    }

    @Override
    public void delete(AggregateId aggregateId) {
        repository.deleteByAggregateId_AggregateId(aggregateId.getId());
    }
}
