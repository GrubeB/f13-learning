package pl.app.property.accommodation_availability.application.port.in;

import pl.app.property.accommodation_availability.application.port.in.command.ManualAssignTypeReservationCommand;

public interface ManualAssignAccommodationTypeReservationUseCase {
    void manualAssign(ManualAssignTypeReservationCommand command);
}
