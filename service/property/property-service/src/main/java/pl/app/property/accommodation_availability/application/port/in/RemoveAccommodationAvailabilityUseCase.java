package pl.app.property.accommodation_availability.application.port.in;

import pl.app.property.accommodation_availability.application.port.in.command.RemoveAccommodationAvailabilityCommand;

public interface RemoveAccommodationAvailabilityUseCase {
    void removeAccommodationAvailability(RemoveAccommodationAvailabilityCommand command);
}
