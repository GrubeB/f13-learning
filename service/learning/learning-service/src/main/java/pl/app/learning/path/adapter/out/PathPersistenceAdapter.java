package pl.app.learning.path.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.event.DelayedDomainEventPublisher;
import pl.app.common.ddd.event.DomainEventPublisherFactory;
import pl.app.learning.path.application.domain.Path;
import pl.app.learning.path.application.domain.PathException;
import pl.app.learning.path.application.port.out.PathDomainRepositoryPort;

@Component
@Transactional
@RequiredArgsConstructor
class PathPersistenceAdapter implements
        PathDomainRepositoryPort {
    private final PathRepository repository;
    private final DomainEventPublisherFactory domainEventPublisherFactory;

    @Override
    public Path load(AggregateId aggregateId) {
        Path entity = repository.findById(aggregateId)
                .orElseThrow(() -> PathException.NotFoundPathException.fromId(aggregateId.getId()));
        entity.setEventPublisher(domainEventPublisherFactory.getEventPublisher());
        return entity;
    }

    @Override
    public void save(Path aggregate) {
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
