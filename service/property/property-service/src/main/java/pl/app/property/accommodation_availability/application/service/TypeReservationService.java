package pl.app.property.accommodation_availability.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.app.common.cqrs.command.annotation.CommandHandlerAnnotation;
import pl.app.common.cqrs.command.annotation.CommandHandlingAnnotation;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.shared.DateRange;
import pl.app.property.accommodation_availability.application.domain.AccommodationTypeAvailability;
import pl.app.property.accommodation_availability.application.domain.AccommodationTypeReservation;
import pl.app.property.accommodation_availability.application.port.in.CreateAccommodationTypeReservationUseCase;
import pl.app.property.accommodation_availability.application.port.in.RemoveAccommodationTypeReservationUseCase;
import pl.app.property.accommodation_availability.application.port.in.command.CreateTypeReservationCommand;
import pl.app.property.accommodation_availability.application.port.in.command.RemoveTypeReservationCommand;
import pl.app.property.accommodation_availability.application.port.out.AccommodationAvailabilityRepositoryPort;

import java.util.UUID;

@Component("accommodationTypeReservationService")
@RequiredArgsConstructor
@CommandHandlerAnnotation
public class TypeReservationService implements
        RemoveAccommodationTypeReservationUseCase,
        CreateAccommodationTypeReservationUseCase {

    private final AccommodationAvailabilityRepositoryPort repositoryPort;

    @Override
    @CommandHandlingAnnotation
    public UUID createTypeReservation(CreateTypeReservationCommand command) {
        AccommodationTypeAvailability availability = repositoryPort
                .loadByAccommodationTypeId(new AggregateId(command.getAccommodationTypeId()), new DateRange<>(command.getStartDate(), command.getEndDate()));
        AccommodationTypeReservation newTypeReservation = availability.createTypeReservation(new DateRange<>(command.getStartDate(), command.getEndDate()));
        repositoryPort.save(availability);
        return newTypeReservation.getId();
    }

    @Override
    @CommandHandlingAnnotation
    public void removeTypeReservation(RemoveTypeReservationCommand command) {
        AccommodationTypeAvailability availability = repositoryPort
                .loadByTypeReservationId(new AggregateId(command.getTypeReservationId()));
        availability.removeTypeReservation(new AggregateId(command.getTypeReservationId()));
        repositoryPort.save(availability);
    }
}
