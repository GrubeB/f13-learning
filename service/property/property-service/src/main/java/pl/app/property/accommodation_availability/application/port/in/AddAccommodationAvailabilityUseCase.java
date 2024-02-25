package pl.app.property.accommodation_availability.application.port.in;

import pl.app.property.accommodation_availability.application.port.in.command.AddAccommodationAvailabilityCommand;

public interface AddAccommodationAvailabilityUseCase {
    void addAccommodationAvailability(AddAccommodationAvailabilityCommand command);
}
