package pl.app.property.accommodation_availability.application.port.in;

import pl.app.property.accommodation_availability.application.port.in.command.CreateRestrictionCommand;

import java.util.UUID;

public interface CreateAccommodationReservationUseCase {
    UUID createRestriction(CreateRestrictionCommand command);
}
