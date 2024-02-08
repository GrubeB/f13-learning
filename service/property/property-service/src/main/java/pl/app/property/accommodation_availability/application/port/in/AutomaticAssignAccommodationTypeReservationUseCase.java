package pl.app.property.accommodation_availability.application.port.in;

import pl.app.property.accommodation_availability.application.port.in.command.AutomaticAssignTypeReservationCommand;

public interface AutomaticAssignAccommodationTypeReservationUseCase {
    void automaticAssign(AutomaticAssignTypeReservationCommand command);
}
