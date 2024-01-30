package pl.app.property.accommodation_type.application.port.in;

import pl.app.cqrs.command.annotation.CommandHandlerAnnotation;
import pl.app.cqrs.command.annotation.CommandHandlingAnnotation;
import pl.app.property.accommodation_type.application.port.in.command.CreateAccommodationTypeCommand;

import java.util.UUID;
public interface CreateAccommodationTypeUseCase {
    UUID createAccommodationType(CreateAccommodationTypeCommand command);
}
