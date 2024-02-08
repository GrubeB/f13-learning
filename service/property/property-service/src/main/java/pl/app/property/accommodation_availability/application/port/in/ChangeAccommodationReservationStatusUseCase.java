package pl.app.property.accommodation_availability.application.port.in;

import pl.app.property.accommodation_availability.application.port.in.command.ChangeRestrictionStatusCommand;

public interface ChangeAccommodationReservationStatusUseCase {
    void changeRestrictionStatus(ChangeRestrictionStatusCommand command);
}
