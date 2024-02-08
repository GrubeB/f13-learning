package pl.app.property.accommodation_availability.application.port.in;

import pl.app.property.accommodation_availability.application.port.in.command.RemoveTypeReservationCommand;

public interface RemoveAccommodationTypeReservationUseCase {
    void removeTypeReservation(RemoveTypeReservationCommand command);
}
