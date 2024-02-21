package pl.app.learning.reference.adapter.out.persistance;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.event.DelayedDomainEventPublisher;
import pl.app.common.ddd.event.DomainEventPublisherFactory;
import pl.app.learning.reference.application.domain.Reference;
import pl.app.learning.reference.application.domain.ReferenceException;
import pl.app.learning.reference.application.port.out.ReferenceDomainRepositoryPort;
import pl.app.learning.topic.adapter.out.persistance.TopicRepository;
import pl.app.learning.topic.adapter.out.persistance.TopicSnapshotRepository;
import pl.app.learning.topic.application.domain.Topic;
import pl.app.learning.topic.application.domain.TopicException;
import pl.app.learning.topic.application.port.out.TopicDomainRepositoryPort;

@Component
@Transactional
@RequiredArgsConstructor
class ReferencePersistenceAdapter implements
        ReferenceDomainRepositoryPort {
    private final ReferenceRepository repository;
    private final DomainEventPublisherFactory domainEventPublisherFactory;

    @Override
    public Reference load(AggregateId aggregateId) {
        Reference entity = repository.findById(aggregateId)
                .orElseThrow(() -> ReferenceException.NotFoundReferenceException.fromId(aggregateId.getId()));
        entity.setEventPublisher(domainEventPublisherFactory.getEventPublisher());
        return entity;
    }

    @Override
    public void save(Reference aggregate) {
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
