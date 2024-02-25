package pl.app.property.accommodation_availability.application.port.in.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.app.property.accommodation_availability.application.domain.AccommodationRestrictionStatus;

import java.io.Serializable;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChangeRestrictionStatusCommand implements Serializable {
    private UUID restrictionId;
    private AccommodationRestrictionStatus status;
}