package pl.app.property.accommodation_availability.application.port.in;

import pl.app.property.accommodation_availability.application.port.in.command.CreateAccommodationTypeAvailabilityCommand;

import java.util.UUID;

public interface CreateAccommodationTypeAvailabilityUseCase {
    UUID create(CreateAccommodationTypeAvailabilityCommand command);
}
