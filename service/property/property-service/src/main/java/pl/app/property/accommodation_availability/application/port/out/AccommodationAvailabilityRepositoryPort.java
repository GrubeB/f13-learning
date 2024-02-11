package pl.app.property.accommodation_availability.application.port.out;


import pl.app.ddd.AggregateId;
import pl.app.ddd.annotation.DomainRepositoryAnnotation;
import pl.app.ddd.shared.DateRange;
import pl.app.property.accommodation_availability.application.domain.model.AccommodationTypeAvailability;

import java.time.LocalDate;
import java.util.UUID;

@DomainRepositoryAnnotation
public interface AccommodationAvailabilityRepositoryPort {
    AccommodationTypeAvailability load(AggregateId aggregateId);
    AccommodationTypeAvailability load(AggregateId aggregateId, DateRange<LocalDate> dateRange);
    AccommodationTypeAvailability loadByAccommodationTypeId(AggregateId accommodationTypeId);

    AccommodationTypeAvailability loadByAccommodationTypeId(AggregateId accommodationTypeId, DateRange<LocalDate> dateRange);

    AccommodationTypeAvailability loadByAccommodationId(UUID accommodationId);

    AccommodationTypeAvailability loadByAccommodationId(UUID accommodationId, DateRange<LocalDate> dateRange);

    AccommodationTypeAvailability loadByTypeReservationId(UUID typeReservationId);

    AccommodationTypeAvailability loadByTypeReservationId(UUID typeReservationId, DateRange<LocalDate> dateRange);

    AccommodationTypeAvailability loadByRestrictionId(UUID restrictionId);

    AccommodationTypeAvailability loadByRestrictionId(UUID restrictionId, DateRange<LocalDate> dateRange);

    void save(AccommodationTypeAvailability accommodationTypeAvailability);
}
