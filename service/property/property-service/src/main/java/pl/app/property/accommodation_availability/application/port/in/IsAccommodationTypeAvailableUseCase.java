package pl.app.property.accommodation_availability.application.port.in;

import pl.app.property.accommodation_availability.application.port.in.command.IsAccommodationTypeAvailableCommand;

public interface IsAccommodationTypeAvailableUseCase {
    Boolean isAccommodationTypeAvailable(IsAccommodationTypeAvailableCommand command);
}
