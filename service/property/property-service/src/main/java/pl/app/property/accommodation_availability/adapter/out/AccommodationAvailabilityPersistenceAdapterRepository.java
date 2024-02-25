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
import pl.app.property.accommodation_availability.application.domain.AccommodationTypeAvailability;
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
        return repository.findIdByAccommodationType_AccommodationType(accommodationTypeId)
                .map(this::load)
                .orElseThrow(AccommodationAvailabilityException.NotFoundAccommodationTypeAvailabilityException::new);
    }

    @Override
    public AccommodationTypeAvailability loadByAccommodationTypeId(AggregateId accommodationTypeId, DateRange<LocalDate> dateRange) {
        return repository.findIdByAccommodationType_AccommodationType(accommodationTypeId)
                .map(aggregateId -> load(aggregateId, dateRange))
                .orElseThrow(AccommodationAvailabilityException.NotFoundAccommodationTypeAvailabilityException::new);
    }

    @Override
    public AccommodationTypeAvailability loadByAccommodationId(AggregateId accommodationId) {
        return repository.findIdByAccommodationAvailabilities_Accommodation(accommodationId)
                .map(this::load)
                .orElseThrow(AccommodationAvailabilityException.NotFoundAccommodationTypeAvailabilityException::new);
    }

    @Override
    public AccommodationTypeAvailability loadByAccommodationId(AggregateId accommodationId, DateRange<LocalDate> dateRange) {
        return repository.findIdByAccommodationAvailabilities_Accommodation(accommodationId)
                .map(aggregateId -> load(aggregateId, dateRange))
                .orElseThrow(AccommodationAvailabilityException.NotFoundAccommodationTypeAvailabilityException::new);
    }

    @Override
    public AccommodationTypeAvailability loadByTypeReservationId(AggregateId typeReservationId) {
        return repository.findIdByTypeReservations(typeReservationId)
                .map(this::load)
                .orElseThrow(AccommodationAvailabilityException.NotFoundAccommodationTypeAvailabilityException::new);
    }

    @Override
    public AccommodationTypeAvailability loadByTypeReservationId(AggregateId typeReservationId, DateRange<LocalDate> dateRange) {
        return repository.findIdByTypeReservations(typeReservationId)
                .map(aggregateId -> load(aggregateId, dateRange))
                .orElseThrow(AccommodationAvailabilityException.NotFoundAccommodationTypeAvailabilityException::new);
    }

    @Override
    public AccommodationTypeAvailability loadByRestrictionId(UUID restrictionId) {
        return repository.findIdByAccommodationAvailabilities_AccommodationRestrictionId(restrictionId)
                .map(this::load)
                .orElseThrow(AccommodationAvailabilityException.NotFoundAccommodationTypeAvailabilityException::new);
    }

    @Override
    public AccommodationTypeAvailability loadByRestrictionId(UUID restrictionId, DateRange<LocalDate> dateRange) {
        return repository.findIdByAccommodationAvailabilities_AccommodationRestrictionId(restrictionId)
                .map(aggregateId -> load(aggregateId, dateRange))
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
