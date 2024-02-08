package pl.app.property.accommodation_availability.application.port.in;

import pl.app.property.accommodation_availability.application.port.in.command.UnassignTypeReservationCommand;

public interface UnassignAccommodationTypeReservationUseCase {
    void unassign(UnassignTypeReservationCommand command);
}
