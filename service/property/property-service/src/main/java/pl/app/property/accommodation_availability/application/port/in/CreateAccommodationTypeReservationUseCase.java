package pl.app.property.accommodation_availability.application.port.in;

import pl.app.property.accommodation_availability.application.port.in.command.CreateTypeReservationCommand;

import java.util.UUID;

public interface CreateAccommodationTypeReservationUseCase {
    UUID createTypeReservation(CreateTypeReservationCommand command);
}
