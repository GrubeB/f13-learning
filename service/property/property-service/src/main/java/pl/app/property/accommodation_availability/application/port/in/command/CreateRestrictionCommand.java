package pl.app.property.accommodation_availability.application.port.in.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.app.property.accommodation_availability.application.domain.AccommodationRestrictionStatus;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateRestrictionCommand implements Serializable {
    private UUID accommodationId;
    private LocalDate startDate;
    private LocalDate endDate;
    private AccommodationRestrictionStatus status;
}