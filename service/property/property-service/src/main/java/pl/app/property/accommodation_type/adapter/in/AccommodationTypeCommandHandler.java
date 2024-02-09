package pl.app.property.accommodation_type.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.app.cqrs.command.annotation.CommandHandlerAnnotation;
import pl.app.cqrs.command.annotation.CommandHandlingAnnotation;
import pl.app.property.accommodation_type.application.port.in.AddAccommodationUseCase;
import pl.app.property.accommodation_type.application.port.in.CreateAccommodationTypeUseCase;
import pl.app.property.accommodation_type.application.port.in.RemoveAccommodationUseCase;
import pl.app.property.accommodation_type.application.port.in.command.AddAccommodationCommand;
import pl.app.property.accommodation_type.application.port.in.command.CreateAccommodationTypeCommand;
import pl.app.property.accommodation_type.application.port.in.command.RemoveAccommodationCommand;

import java.util.UUID;

@CommandHandlerAnnotation
@Component
@RequiredArgsConstructor
public class AccommodationTypeCommandHandler {
    private final AddAccommodationUseCase addAccommodationUseCase;
    private final CreateAccommodationTypeUseCase createAccommodationTypeUseCase;
    private final RemoveAccommodationUseCase removeAccommodationUseCase;

    @CommandHandlingAnnotation
    public UUID handleAddAccommodationCommand(AddAccommodationCommand command) {
        return addAccommodationUseCase.addAccommodation(command);
    }

    @CommandHandlingAnnotation
    public UUID handleCreateAccommodationTypeCommand(CreateAccommodationTypeCommand command) {
        return createAccommodationTypeUseCase.createAccommodationType(command);
    }

    @CommandHandlingAnnotation
    public void handleRemoveAccommodationCommand(RemoveAccommodationCommand command) {
        removeAccommodationUseCase.removeAccommodation(command);
    }
}
