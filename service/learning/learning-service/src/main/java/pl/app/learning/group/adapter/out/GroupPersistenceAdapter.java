package pl.app.learning.group.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.event.DelayedDomainEventPublisher;
import pl.app.common.ddd.event.DomainEventPublisherFactory;
import pl.app.learning.group.application.domain.Group;
import pl.app.learning.group.application.port.out.GroupDomainRepositoryPort;
import pl.app.learning.group_snapshot.adapter.out.GroupSnapshotRepository;
import pl.app.learning.topic.application.domain.TopicException;

@Component
@Transactional
@RequiredArgsConstructor
class GroupPersistenceAdapter implements
        GroupDomainRepositoryPort {
    private final GroupRepository repository;
    private final GroupSnapshotRepository topicSnapshotRepository;
    private final DomainEventPublisherFactory domainEventPublisherFactory;

    @Override
    public Group load(AggregateId aggregateId) {
        Group entity = repository.findById(aggregateId)
                .orElseThrow(() -> TopicException.NotFoundTopicException.fromId(aggregateId.getId()));
        entity.setEventPublisher(domainEventPublisherFactory.getEventPublisher());
        entity.makeAndStoreSnapshot();
        return entity;
    }

    @Override
    public void save(Group aggregate) {
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
