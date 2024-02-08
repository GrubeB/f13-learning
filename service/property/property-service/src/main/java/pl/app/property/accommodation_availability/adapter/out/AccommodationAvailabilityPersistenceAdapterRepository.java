package pl.app.property.accommodation_availability.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.ddd.AggregateId;
import pl.app.ddd.shared.DateRange;
import pl.app.property.accommodation_availability.adapter.out.persistence.AccommodationAvailabilityMapper;
import pl.app.property.accommodation_availability.adapter.out.persistence.AccommodationTypeAvailabilityEntityRepository;
import pl.app.property.accommodation_availability.adapter.out.persistence.AccommodationTypeAvailabilityEntity;
import pl.app.property.accommodation_availability.application.domain.model.AccommodationAvailabilityException;
import pl.app.property.accommodation_availability.application.domain.model.AccommodationTypeAvailability;
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

    @Override
    public AccommodationTypeAvailability loadByAccommodationTypeId(AggregateId accommodationTypeId) {
        AccommodationTypeAvailabilityEntity entity = repository.findByAccommodationType_AccommodationTypeId(accommodationTypeId.getId())
                .orElseThrow(AccommodationAvailabilityException.NotFoundAccommodationTypeAvailabilityException::new);
        return mapper.map(entity, AccommodationTypeAvailability.class);
    }

    @Override
    public AccommodationTypeAvailability loadByAccommodationTypeId(AggregateId accommodationTypeId, DateRange<LocalDate> dateRange) {
        AccommodationTypeAvailabilityEntity entity = repository.findByAccommodationType_AccommodationTypeId(accommodationTypeId.getId())
                .orElseThrow(AccommodationAvailabilityException.NotFoundAccommodationTypeAvailabilityException::new);
        return mapper.map(entity, AccommodationTypeAvailability.class);
    }

    @Override
    public AccommodationTypeAvailability loadByAccommodationId(UUID accommodationId) {
        AccommodationTypeAvailabilityEntity entity = repository.findByAccommodationType_Accommodations_AccommodationId(accommodationId)
                .orElseThrow(AccommodationAvailabilityException.NotFoundAccommodationTypeAvailabilityException::new);
        return mapper.map(entity, AccommodationTypeAvailability.class);
    }

    @Override
    public AccommodationTypeAvailability loadByAccommodationId(UUID accommodationId, DateRange<LocalDate> dateRange) {
        AccommodationTypeAvailabilityEntity entity = repository.findByAccommodationType_Accommodations_AccommodationId(accommodationId)
                .orElseThrow(AccommodationAvailabilityException.NotFoundAccommodationTypeAvailabilityException::new);
        return mapper.map(entity, AccommodationTypeAvailability.class);
    }

    @Override
    public AccommodationTypeAvailability loadByTypeReservationId(UUID typeReservationId) {
        AccommodationTypeAvailabilityEntity entity = repository.findByAccommodationTypeReservations_AccommodationTypeReservationId(typeReservationId)
                .orElseThrow(AccommodationAvailabilityException.NotFoundAccommodationTypeAvailabilityException::new);
        return mapper.map(entity, AccommodationTypeAvailability.class);
    }

    @Override
    public AccommodationTypeAvailability loadByTypeReservationId(UUID typeReservationId, DateRange<LocalDate> dateRange) {
        AccommodationTypeAvailabilityEntity entity = repository.findByAccommodationTypeReservations_AccommodationTypeReservationId(typeReservationId)
                .orElseThrow(AccommodationAvailabilityException.NotFoundAccommodationTypeAvailabilityException::new);
        return mapper.map(entity, AccommodationTypeAvailability.class);
    }

    @Override
    public AccommodationTypeAvailability loadByRestrictionId(UUID reservationId) {
        AccommodationTypeAvailabilityEntity entity = repository.findByAccommodationRestrictions_AccommodationRestrictionId(reservationId)
                .orElseThrow(AccommodationAvailabilityException.NotFoundAccommodationTypeAvailabilityException::new);
        return mapper.map(entity, AccommodationTypeAvailability.class);
    }

    @Override
    public AccommodationTypeAvailability loadByRestrictionId(UUID reservationId, DateRange<LocalDate> dateRange) {
        AccommodationTypeAvailabilityEntity entity = repository.findByAccommodationRestrictions_AccommodationRestrictionId(reservationId)
                .orElseThrow(AccommodationAvailabilityException.NotFoundAccommodationTypeAvailabilityException::new);
        return mapper.map(entity, AccommodationTypeAvailability.class);
    }

    private AccommodationTypeAvailability load(AggregateId aggregateId) {
        AccommodationTypeAvailabilityEntity entity = repository.findById(aggregateId.getId())
                .orElseThrow(() -> AccommodationAvailabilityException.NotFoundAccommodationTypeAvailabilityException.fromId(aggregateId.getId()));
        return mapper.map(entity, AccommodationTypeAvailability.class);
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
