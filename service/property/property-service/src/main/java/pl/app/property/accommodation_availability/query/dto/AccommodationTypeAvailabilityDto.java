package pl.app.property.accommodation_availability.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.shared.DateRange;
import pl.app.common.shared.dto.BaseDto;
import pl.app.property.accommodation_availability.application.domain.model.AccommodationRestrictionStatus;
import pl.app.property.accommodation_availability.application.domain.model.TypeReservationAssignedStatus;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationTypeAvailabilityDto implements Serializable {
    private UUID id;
    private BaseDto propertyId;
    private BaseDto accommodationTypeId;
    private Set<AccommodationAvailabilityDto> accommodationAvailabilities;
    private Set<AccommodationTypeReservationDto> typeReservations;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AccommodationTypeReservationDto implements Serializable {
        private UUID id;
        private DateRange<LocalDate> dateRange;
        private TypeReservationAssignedStatus assignedStatus;
        private Set<AccommodationTypeReservationItemDto> reservationItems;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AccommodationTypeReservationItemDto implements Serializable {
        private UUID id;
        private AccommodationAvailabilityDto accommodationAvailability;
        private AccommodationRestrictionDto restriction;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AccommodationAvailabilityDto implements Serializable {
        private UUID id;
        private AggregateId accommodationId;
        private Set<AccommodationRestrictionDto> restrictions;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AccommodationRestrictionDto implements Serializable {
        private UUID id;
        private AccommodationRestrictionStatus status;
        private DateRange<LocalDate> dateRange;
    }
}
