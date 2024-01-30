package pl.app.property.accommodation_type.application.port.in;

import pl.app.property.accommodation_type.application.port.in.command.RemoveAccommodationCommand;

public interface RemoveAccommodationUseCase {
    void removeAccommodation(RemoveAccommodationCommand command);
}
