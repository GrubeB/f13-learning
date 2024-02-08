package pl.app.property.accommodation_availability.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.app.cqrs.command.annotation.CommandHandlerAnnotation;
import pl.app.cqrs.command.annotation.CommandHandlingAnnotation;
import pl.app.ddd.shared.DateRange;
import pl.app.property.accommodation_availability.application.domain.model.Accommodation;
import pl.app.property.accommodation_availability.application.domain.model.AccommodationRestriction;
import pl.app.property.accommodation_availability.application.domain.model.AccommodationTypeAvailability;
import pl.app.property.accommodation_availability.application.port.in.ChangeAccommodationReservationStatusUseCase;
import pl.app.property.accommodation_availability.application.port.in.CreateAccommodationReservationUseCase;
import pl.app.property.accommodation_availability.application.port.in.RemoveAccommodationReservationUseCase;
import pl.app.property.accommodation_availability.application.port.in.command.ChangeRestrictionStatusCommand;
import pl.app.property.accommodation_availability.application.port.in.command.CreateRestrictionCommand;
import pl.app.property.accommodation_availability.application.port.in.command.RemoveRestrictionCommand;
import pl.app.property.accommodation_availability.application.port.out.AccommodationAvailabilityRepositoryPort;

import java.util.UUID;

@Component("accommodationRestrictionService")
@RequiredArgsConstructor
@CommandHandlerAnnotation
class RestrictionService implements
        RemoveAccommodationReservationUseCase,
        ChangeAccommodationReservationStatusUseCase,
        CreateAccommodationReservationUseCase {

    private final AccommodationAvailabilityRepositoryPort repositoryPort;

    @Override
    @CommandHandlingAnnotation
    public UUID createRestriction(CreateRestrictionCommand command) {
        AccommodationTypeAvailability availability = repositoryPort
                .loadByAccommodationId(command.getAccommodationId(), new DateRange<>(command.getStartDate(), command.getEndDate()));
        Accommodation accommodation = availability.getAccommodationById(command.getAccommodationId());
        AccommodationRestriction newReservation = accommodation.createRestriction(new DateRange<>(command.getStartDate(), command.getEndDate()), command.getStatus());
        repositoryPort.save(availability);
        return newReservation.getId();
    }

    @Override
    @CommandHandlingAnnotation
    public void removeRestriction(RemoveRestrictionCommand command) {
        AccommodationTypeAvailability availability = repositoryPort.loadByRestrictionId(command.getReservationId());
        Accommodation accommodation = availability.getAccommodationByRestrictionId(command.getReservationId());
        accommodation.removeRestriction(command.getReservationId());
        repositoryPort.save(availability);
    }

    @Override
    @CommandHandlingAnnotation
    public void changeRestrictionStatus(ChangeRestrictionStatusCommand command) {
        AccommodationTypeAvailability availability = repositoryPort
                .loadByRestrictionId(command.getRestrictionId());
        AccommodationRestriction restriction = availability.getRestrictionById(command.getRestrictionId());
        restriction.changeStatusTo(command.getStatus());
        repositoryPort.save(availability);
    }
}
