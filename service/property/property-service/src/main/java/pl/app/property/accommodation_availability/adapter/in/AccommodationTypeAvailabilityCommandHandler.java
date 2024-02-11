package pl.app.property.accommodation_availability.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.app.common.cqrs.command.annotation.CommandHandlerAnnotation;
import pl.app.common.cqrs.command.annotation.CommandHandlingAnnotation;
import pl.app.property.accommodation_availability.application.port.in.*;
import pl.app.property.accommodation_availability.application.port.in.command.*;

import java.util.UUID;

@CommandHandlerAnnotation
@Component
@RequiredArgsConstructor
public class AccommodationTypeAvailabilityCommandHandler {

    private final AutomaticAssignAccommodationTypeReservationUseCase automaticAssignAccommodationTypeReservationUseCase;
    private final ChangeAccommodationReservationStatusUseCase changeAccommodationReservationStatusUseCase;
    private final CreateAccommodationReservationUseCase createAccommodationReservationUseCase;
    private final CreateAccommodationTypeAvailabilityUseCase createAccommodationTypeAvailabilityUseCase;
    private final CreateAccommodationTypeReservationUseCase createAccommodationTypeReservationUseCase;
    private final IsAccommodationAvailableUseCase isAccommodationAvailableUseCase;
    private final IsAccommodationTypeAvailableUseCase isAccommodationTypeAvailableUseCase;
    private final ManualAssignAccommodationTypeReservationUseCase manualAssignAccommodationTypeReservationUseCase;
    private final RemoveAccommodationReservationUseCase removeAccommodationReservationUseCase;
    private final RemoveAccommodationTypeReservationUseCase removeAccommodationTypeReservationUseCase;
    private final UnassignAccommodationTypeReservationUseCase unassignAccommodationTypeReservationUseCase;

    @CommandHandlingAnnotation
    public void handleAutomaticAssignTypeReservationCommand(AutomaticAssignTypeReservationCommand command) {
        automaticAssignAccommodationTypeReservationUseCase.automaticAssign(command);
    }

    @CommandHandlingAnnotation
    public void handleChangeRestrictionStatusCommand(ChangeRestrictionStatusCommand command) {
        changeAccommodationReservationStatusUseCase.changeRestrictionStatus(command);
    }

    @CommandHandlingAnnotation
    public UUID handleCreateRestrictionCommand(CreateRestrictionCommand command) {
        return createAccommodationReservationUseCase.createRestriction(command);
    }

    @CommandHandlingAnnotation
    public UUID handleCreateAccommodationTypeAvailabilityCommand(CreateAccommodationTypeAvailabilityCommand command) {
        return createAccommodationTypeAvailabilityUseCase.create(command);
    }

    @CommandHandlingAnnotation
    public UUID handleCreateTypeReservationCommand(CreateTypeReservationCommand command) {
        return createAccommodationTypeReservationUseCase.createTypeReservation(command);
    }

    @CommandHandlingAnnotation
    public Boolean handleIsAccommodationAvailableCommand(IsAccommodationAvailableCommand command) {
        return isAccommodationAvailableUseCase.isAccommodationAvailable(command);
    }

    @CommandHandlingAnnotation
    public Boolean handleIsAccommodationTypeAvailableCommand(IsAccommodationTypeAvailableCommand command) {
        return isAccommodationTypeAvailableUseCase.isAccommodationTypeAvailable(command);
    }

    @CommandHandlingAnnotation
    public void handleManualAssignTypeReservationCommand(ManualAssignTypeReservationCommand command) {
        manualAssignAccommodationTypeReservationUseCase.manualAssign(command);
    }

    @CommandHandlingAnnotation
    public void handleRemoveRestrictionCommand(RemoveRestrictionCommand command) {
        removeAccommodationReservationUseCase.removeRestriction(command);
    }

    @CommandHandlingAnnotation
    public void handleRemoveTypeReservationCommand(RemoveTypeReservationCommand command) {
        removeAccommodationTypeReservationUseCase.removeTypeReservation(command);
    }

    @CommandHandlingAnnotation
    public void handleUnassignTypeReservationCommand(UnassignTypeReservationCommand command) {
        unassignAccommodationTypeReservationUseCase.unassign(command);
    }
}
