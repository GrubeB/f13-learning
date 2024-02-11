package pl.app.property.accommodation_availability.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.shared.DateRange;
import pl.app.property.accommodation_availability.application.domain.model.AccommodationTypeAvailability;
import pl.app.property.accommodation_availability.application.domain.model.AccommodationTypeReservation;
import pl.app.property.accommodation_availability.application.port.in.CreateAccommodationTypeReservationUseCase;
import pl.app.property.accommodation_availability.application.port.in.RemoveAccommodationTypeReservationUseCase;
import pl.app.property.accommodation_availability.application.port.in.command.CreateTypeReservationCommand;
import pl.app.property.accommodation_availability.application.port.in.command.RemoveTypeReservationCommand;
import pl.app.property.accommodation_availability.application.port.out.AccommodationAvailabilityRepositoryPort;

import java.util.UUID;

@Component("accommodationTypeReservationService")
@RequiredArgsConstructor
class TypeReservationService implements
        RemoveAccommodationTypeReservationUseCase,
        CreateAccommodationTypeReservationUseCase {

    private final AccommodationAvailabilityRepositoryPort repositoryPort;

    @Override
    public UUID createTypeReservation(CreateTypeReservationCommand command) {
        AccommodationTypeAvailability availability = repositoryPort
                .loadByAccommodationTypeId(new AggregateId(command.getAccommodationTypeId()), new DateRange<>(command.getStartDate(), command.getEndDate()));
        AccommodationTypeReservation newTypeReservation = availability.createTypeReservation(new DateRange<>(command.getStartDate(), command.getEndDate()));
        availability.tryToAutoAssignTypeReservation(newTypeReservation);
        repositoryPort.save(availability);
        return newTypeReservation.getId();
    }

    @Override
    public void removeTypeReservation(RemoveTypeReservationCommand command) {
        AccommodationTypeAvailability availability = repositoryPort
                .loadByTypeReservationId(command.getTypeReservationId());
        availability.removeTypeReservation(command.getTypeReservationId());
        repositoryPort.save(availability);
    }
}
