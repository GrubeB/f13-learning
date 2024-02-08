package pl.app.property.accommodation_availability.application.port.in.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ManualAssignTypeReservationCommand implements Serializable {
    private UUID typeReservationId;
    private Set<AccommodationReservation> reservations;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AccommodationReservation {
        private UUID accommodationId;
        private LocalDate startDate;
        private LocalDate endDate;
    }
}