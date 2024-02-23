package pl.app.property.accommodation_type.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.event.DelayedDomainEventPublisher;
import pl.app.common.ddd.event.DomainEventPublisherFactory;
import pl.app.property.accommodation_type.application.domain.AccommodationType;
import pl.app.property.accommodation_type.application.domain.AccommodationTypeException;
import pl.app.property.accommodation_type.application.port.out.AccommodationTypeRepositoryPort;

@Component
@Transactional
@RequiredArgsConstructor
class AccommodationTypePersistenceAdapter implements
        AccommodationTypeRepositoryPort {
    private final AccommodationTypeRepository repository;
    private final DomainEventPublisherFactory domainEventPublisherFactory;

    @Override
    public AccommodationType load(AggregateId aggregateId) {
        AccommodationType entity = repository.findById(aggregateId)
                .orElseThrow(() -> AccommodationTypeException.NotFoundAccommodationTypeException.fromId(aggregateId.getId()));
        entity.setEventPublisher(domainEventPublisherFactory.getEventPublisher());
        return entity;
    }

    @Override
    public void save(AccommodationType aggregate) {
        repository.saveAndFlush(aggregate);
        if (aggregate.getEventPublisher() instanceof DelayedDomainEventPublisher publisher) {
            publisher.publishDelayedEvents();
        }
    }

    public void delete(AggregateId aggregateId) {
        repository.deleteByAggregateId_AggregateId(aggregateId.getId());
    }
}
