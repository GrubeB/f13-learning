package pl.app.property.accommodation_availability.application.port.out;


import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.annotation.DomainRepositoryAnnotation;
import pl.app.common.ddd.shared.DateRange;
import pl.app.property.accommodation_availability.application.domain.AccommodationTypeAvailability;

import java.time.LocalDate;
import java.util.UUID;

@DomainRepositoryAnnotation
public interface AccommodationAvailabilityRepositoryPort {
    AccommodationTypeAvailability load(AggregateId aggregateId);

    AccommodationTypeAvailability load(AggregateId aggregateId, DateRange<LocalDate> dateRange);

    AccommodationTypeAvailability loadByAccommodationTypeId(AggregateId accommodationTypeId);

    AccommodationTypeAvailability loadByAccommodationTypeId(AggregateId accommodationTypeId, DateRange<LocalDate> dateRange);

    AccommodationTypeAvailability loadByAccommodationId(AggregateId accommodationId);

    AccommodationTypeAvailability loadByAccommodationId(AggregateId accommodationId, DateRange<LocalDate> dateRange);

    AccommodationTypeAvailability loadByTypeReservationId(AggregateId typeReservationId);

    AccommodationTypeAvailability loadByTypeReservationId(AggregateId typeReservationId, DateRange<LocalDate> dateRange);

    AccommodationTypeAvailability loadByRestrictionId(UUID restrictionId);

    AccommodationTypeAvailability loadByRestrictionId(UUID restrictionId, DateRange<LocalDate> dateRange);

    void save(AccommodationTypeAvailability accommodationTypeAvailability);
}
