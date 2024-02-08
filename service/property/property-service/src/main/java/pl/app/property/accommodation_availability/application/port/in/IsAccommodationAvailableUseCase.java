package pl.app.property.accommodation_availability.application.port.in;

import pl.app.property.accommodation_availability.application.port.in.command.IsAccommodationAvailableCommand;

public interface IsAccommodationAvailableUseCase {
    Boolean isAccommodationAvailable(IsAccommodationAvailableCommand command);
}
