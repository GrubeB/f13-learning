package pl.app.property.accommodation_availability.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.app.common.ddd.shared.DateRange;
import pl.app.property.accommodation_availability.application.domain.model.AccommodationTypeAvailability;
import pl.app.property.accommodation_availability.application.domain.model.AccommodationTypeReservation;
import pl.app.property.accommodation_availability.application.port.in.AutomaticAssignAccommodationTypeReservationUseCase;
import pl.app.property.accommodation_availability.application.port.in.ManualAssignAccommodationTypeReservationUseCase;
import pl.app.property.accommodation_availability.application.port.in.UnassignAccommodationTypeReservationUseCase;
import pl.app.property.accommodation_availability.application.port.in.command.AutomaticAssignTypeReservationCommand;
import pl.app.property.accommodation_availability.application.port.in.command.ManualAssignTypeReservationCommand;
import pl.app.property.accommodation_availability.application.port.in.command.UnassignTypeReservationCommand;
import pl.app.property.accommodation_availability.application.port.out.AccommodationAvailabilityRepositoryPort;


@Component("accommodationAssignService")
@RequiredArgsConstructor
class AssignService implements
        UnassignAccommodationTypeReservationUseCase,
        AutomaticAssignAccommodationTypeReservationUseCase,
        ManualAssignAccommodationTypeReservationUseCase {

    private final AccommodationAvailabilityRepositoryPort repositoryPort;

    @Override
    public void automaticAssign(AutomaticAssignTypeReservationCommand command) {
        AccommodationTypeAvailability availability = repositoryPort.loadByTypeReservationId(command.getTypeReservationId());
        availability.tryToAutoAssignTypeReservation(command.getTypeReservationId());
        repositoryPort.save(availability);
    }

    @Override
    public void manualAssign(ManualAssignTypeReservationCommand command) {
        AccommodationTypeAvailability availability = repositoryPort.loadByTypeReservationId(command.getTypeReservationId());
        var reservations = command.getReservations().stream()
                .map(r -> new AccommodationTypeAvailability.ReservationRequest(
                        r.getAccommodationId(), new DateRange<>(r.getStartDate(), r.getEndDate())
                )).toList();
        availability.tryToManualAssignTypeReservations(command.getTypeReservationId(), reservations);
        repositoryPort.save(availability);
    }

    @Override
    public void unassign(UnassignTypeReservationCommand command) {
        AccommodationTypeAvailability availability = repositoryPort.loadByTypeReservationId(command.getAccommodationTypeReservationId());
        AccommodationTypeReservation typeReservation = availability.getTypeReservationById(command.getAccommodationTypeReservationId());
        typeReservation.removeAllReservations();
        repositoryPort.save(availability);
    }
}
