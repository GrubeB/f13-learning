package pl.app.property.accommodation_type.application.port.in;

import pl.app.cqrs.command.annotation.CommandHandlerAnnotation;
import pl.app.cqrs.command.annotation.CommandHandlingAnnotation;
import pl.app.property.accommodation_type.application.port.in.command.AddAccommodationCommand;

import java.util.UUID;

public interface AddAccommodationUseCase {
    UUID addAccommodation(AddAccommodationCommand command);
}
