package pl.app.property.accommodation_availability.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.app.common.shared.dto.BaseDto;
import pl.app.property.accommodation_availability.application.domain.model.TypeReservationAssignedStatus;
import pl.app.property.accommodation_availability.application.domain.model.AccommodationRestrictionStatus;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationTypeAvailabilityDto implements Serializable {
    private UUID accommodationTypeAvailabilityId;
    private Set<AccommodationReservationDto> accommodationRestrictions;
    private Set<AccommodationTypeReservationDto> accommodationTypeReservations;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AccommodationReservationDto implements Serializable {
        private UUID accommodationRestrictionId;
        private AccommodationRestrictionStatus status;
        private LocalDate startDate;
        private LocalDate endDate;
        private BaseDto accommodation;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AccommodationTypeReservationDto implements Serializable {
        private UUID accommodationTypeReservationId;
        private LocalDate startDate;
        private LocalDate endDate;
        private TypeReservationAssignedStatus status;
        private Set<AccommodationReservationDto> accommodationRestrictions;
    }
}
