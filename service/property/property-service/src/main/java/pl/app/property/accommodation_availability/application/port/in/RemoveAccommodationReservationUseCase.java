package pl.app.property.accommodation_availability.application.port.in;

import pl.app.property.accommodation_availability.application.port.in.command.RemoveRestrictionCommand;

public interface RemoveAccommodationReservationUseCase {
    void removeRestriction(RemoveRestrictionCommand command);
}
