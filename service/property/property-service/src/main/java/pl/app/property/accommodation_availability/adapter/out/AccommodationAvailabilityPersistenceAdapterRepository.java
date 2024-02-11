package pl.app.property.accommodation_availability.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.ddd.AggregateId;
import pl.app.ddd.shared.DateRange;
import pl.app.property.accommodation_availability.adapter.out.persistence.AccommodationAvailabilityMapper;
import pl.app.property.accommodation_availability.adapter.out.persistence.AccommodationTypeAvailabilityEntity;
import pl.app.property.accommodation_availability.adapter.out.persistence.AccommodationTypeAvailabilityEntityRepository;
import pl.app.property.accommodation_availability.application.domain.model.AccommodationAssignmentPolicy;
import pl.app.property.accommodation_availability.application.domain.model.AccommodationAvailabilityException;
import pl.app.property.accommodation_availability.application.domain.model.AccommodationTypeAvailability;
import pl.app.property.accommodation_availability.application.domain.model.AccommodationTypeAvailabilityPolicy;
import pl.app.property.accommodation_availability.application.port.out.AccommodationAvailabilityRepositoryPort;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
class AccommodationAvailabilityPersistenceAdapterRepository implements
        AccommodationAvailabilityRepositoryPort {
    private final AccommodationTypeAvailabilityEntityRepository repository;
    private final AccommodationAvailabilityMapper mapper;

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
        AccommodationTypeAvailabilityEntity entity = repository.findById(aggregateId.getId())
                .orElseThrow(() -> AccommodationAvailabilityException.NotFoundAccommodationTypeAvailabilityException.fromId(aggregateId.getId()));
        AccommodationTypeAvailability aggregate = mapper.map(entity, AccommodationTypeAvailability.class);
        aggregate.setPolicies(accommodationAssignmentPolicy, accommodationTypeAvailabilityPolicy);
        return aggregate;
    }

    @Override
    public AccommodationTypeAvailability load(AggregateId aggregateId, DateRange<LocalDate> dateRange) {
        AccommodationTypeAvailabilityEntity entity = repository.findById(aggregateId.getId())
                .orElseThrow(() -> AccommodationAvailabilityException.NotFoundAccommodationTypeAvailabilityException.fromId(aggregateId.getId()));
        AccommodationTypeAvailability aggregate = mapper.map(entity, AccommodationTypeAvailability.class);
        aggregate.setPolicies(accommodationAssignmentPolicy, accommodationTypeAvailabilityPolicy);
        return aggregate;
    }

    @Override
    public void save(AccommodationTypeAvailability aggregate) {
        Optional<AccommodationTypeAvailabilityEntity> existingEntity = repository.findById(aggregate.getAggregateId().getId());
        AccommodationTypeAvailabilityEntity mappedAggregate = mapper.map(aggregate, AccommodationTypeAvailabilityEntity.class);
        if (existingEntity.isPresent()) {
            AccommodationTypeAvailabilityEntity mergedEntity = mapper.merge(existingEntity.get(), mappedAggregate);
            repository.save(mergedEntity);
        } else {
            repository.save(mappedAggregate);
        }
    }

}
