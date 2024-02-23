package pl.app.property.accommodation_availability.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.event.DelayedDomainEventPublisher;
import pl.app.common.ddd.event.DomainEventPublisherFactory;
import pl.app.common.ddd.shared.DateRange;
import pl.app.property.accommodation_availability.application.domain.AccommodationAssignmentPolicy;
import pl.app.property.accommodation_availability.application.domain.AccommodationAvailabilityException;
import pl.app.property.accommodation_availability.application.domain.AccommodationTypeAvailabilityPolicy;
import pl.app.property.accommodation_availability.application.domain.model.AccommodationTypeAvailability;
import pl.app.property.accommodation_availability.application.port.out.AccommodationAvailabilityRepositoryPort;

import java.time.LocalDate;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
class AccommodationAvailabilityPersistenceAdapterRepository implements
        AccommodationAvailabilityRepositoryPort {
    private final AccommodationTypeAvailabilityEntityRepository repository;
    private final DomainEventPublisherFactory domainEventPublisherFactory;

    private final AccommodationAssignmentPolicy accommodationAssignmentPolicy;
    private final AccommodationTypeAvailabilityPolicy accommodationTypeAvailabilityPolicy;

    @Override
    public AccommodationTypeAvailability loadByAccommodationTypeId(AggregateId accommodationTypeId) {
        return repository.findIdByAccommodationType_AccommodationTypeId(accommodationTypeId.getId())
                .map(aggregateId -> load(new AggregateId(aggregateId)))
                .orElseThrow(AccommodationAvailabilityException.NotFoundAccommodationTypeAvailabilityException::new);
    }

    @Override
    public AccommodationTypeAvailability loadByAccommodationTypeId(AggregateId accommodationTypeId, DateRange<LocalDate> dateRange) {
        return repository.findIdByAccommodationType_AccommodationTypeId(accommodationTypeId.getId())
                .map(aggregateId -> load(new AggregateId(aggregateId), dateRange))
                .orElseThrow(AccommodationAvailabilityException.NotFoundAccommodationTypeAvailabilityException::new);
    }

    @Override
    public AccommodationTypeAvailability loadByAccommodationId(UUID accommodationId) {
        return repository.findIdByAccommodationType_Accommodations_AccommodationId(accommodationId)
                .map(aggregateId -> load(new AggregateId(aggregateId)))
                .orElseThrow(AccommodationAvailabilityException.NotFoundAccommodationTypeAvailabilityException::new);
    }

    @Override
    public AccommodationTypeAvailability loadByAccommodationId(UUID accommodationId, DateRange<LocalDate> dateRange) {
        return repository.findIdByAccommodationType_Accommodations_AccommodationId(accommodationId)
                .map(aggregateId -> load(new AggregateId(aggregateId), dateRange))
                .orElseThrow(AccommodationAvailabilityException.NotFoundAccommodationTypeAvailabilityException::new);
    }

    @Override
    public AccommodationTypeAvailability loadByTypeReservationId(UUID typeReservationId) {
        return repository.findIdByAccommodationTypeReservations_AccommodationTypeReservationId(typeReservationId)
                .map(aggregateId -> load(new AggregateId(aggregateId)))
                .orElseThrow(AccommodationAvailabilityException.NotFoundAccommodationTypeAvailabilityException::new);
    }

    @Override
    public AccommodationTypeAvailability loadByTypeReservationId(UUID typeReservationId, DateRange<LocalDate> dateRange) {
        return repository.findIdByAccommodationTypeReservations_AccommodationTypeReservationId(typeReservationId)
                .map(aggregateId -> load(new AggregateId(aggregateId), dateRange))
                .orElseThrow(AccommodationAvailabilityException.NotFoundAccommodationTypeAvailabilityException::new);
    }

    @Override
    public AccommodationTypeAvailability loadByRestrictionId(UUID reservationId) {
        return repository.findIdByAccommodationRestrictions_AccommodationRestrictionId(reservationId)
                .map(aggregateId -> load(new AggregateId(aggregateId)))
                .orElseThrow(AccommodationAvailabilityException.NotFoundAccommodationTypeAvailabilityException::new);
    }

    @Override
    public AccommodationTypeAvailability loadByRestrictionId(UUID reservationId, DateRange<LocalDate> dateRange) {
        return repository.findIdByAccommodationRestrictions_AccommodationRestrictionId(reservationId)
                .map(aggregateId -> load(new AggregateId(aggregateId), dateRange))
                .orElseThrow(AccommodationAvailabilityException.NotFoundAccommodationTypeAvailabilityException::new);
    }

    @Override
    public AccommodationTypeAvailability load(AggregateId aggregateId) {
        AccommodationTypeAvailability aggregate = repository.findById(aggregateId)
                .orElseThrow(() -> AccommodationAvailabilityException.NotFoundAccommodationTypeAvailabilityException.fromId(aggregateId.getId()));
        aggregate.setPolicies(accommodationAssignmentPolicy, accommodationTypeAvailabilityPolicy);
        aggregate.setEventPublisher(domainEventPublisherFactory.getEventPublisher());
        return aggregate;
    }

    @Override
    public AccommodationTypeAvailability load(AggregateId aggregateId, DateRange<LocalDate> dateRange) {
        AccommodationTypeAvailability aggregate = repository.findById(aggregateId)
                .orElseThrow(() -> AccommodationAvailabilityException.NotFoundAccommodationTypeAvailabilityException.fromId(aggregateId.getId()));
        aggregate.setPolicies(accommodationAssignmentPolicy, accommodationTypeAvailabilityPolicy);
        aggregate.setEventPublisher(domainEventPublisherFactory.getEventPublisher());
        return aggregate;
    }

    @Override
    public void save(AccommodationTypeAvailability aggregate) {
        repository.saveAndFlush(aggregate);
        if (aggregate.getEventPublisher() instanceof DelayedDomainEventPublisher publisher) {
            publisher.publishDelayedEvents();
        }
    }

}
