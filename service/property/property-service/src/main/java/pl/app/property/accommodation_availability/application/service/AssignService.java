package pl.app.property.accommodation_availability.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.app.common.cqrs.command.annotation.CommandHandlerAnnotation;
import pl.app.common.cqrs.command.annotation.CommandHandlingAnnotation;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.shared.DateRange;
import pl.app.property.accommodation_availability.application.domain.AccommodationTypeAvailability;
import pl.app.property.accommodation_availability.application.domain.AccommodationTypeReservation;
import pl.app.property.accommodation_availability.application.port.in.AutomaticAssignAccommodationTypeReservationUseCase;
import pl.app.property.accommodation_availability.application.port.in.ManualAssignAccommodationTypeReservationUseCase;
import pl.app.property.accommodation_availability.application.port.in.UnassignAccommodationTypeReservationUseCase;
import pl.app.property.accommodation_availability.application.port.in.command.AutomaticAssignTypeReservationCommand;
import pl.app.property.accommodation_availability.application.port.in.command.ManualAssignTypeReservationCommand;
import pl.app.property.accommodation_availability.application.port.in.command.UnassignTypeReservationCommand;
import pl.app.property.accommodation_availability.application.port.out.AccommodationAvailabilityRepositoryPort;


@Component("accommodationAssignService")
@RequiredArgsConstructor
@CommandHandlerAnnotation
public class AssignService implements
        UnassignAccommodationTypeReservationUseCase,
        AutomaticAssignAccommodationTypeReservationUseCase,
        ManualAssignAccommodationTypeReservationUseCase {

    private final AccommodationAvailabilityRepositoryPort repositoryPort;

    @Override
    @CommandHandlingAnnotation
    public void automaticAssign(AutomaticAssignTypeReservationCommand command) {
        AccommodationTypeAvailability availability = repositoryPort.loadByTypeReservationId(new AggregateId(command.getTypeReservationId()));
        availability.autoAssignTypeReservation(new AggregateId(command.getTypeReservationId()));
        repositoryPort.save(availability);
    }

    @Override
    @CommandHandlingAnnotation
    public void manualAssign(ManualAssignTypeReservationCommand command) {
        AccommodationTypeAvailability availability = repositoryPort.loadByTypeReservationId(new AggregateId(command.getTypeReservationId()));
        var reservations = command.getReservations().stream()
                .map(r -> new AccommodationTypeAvailability.ReservationRequest(
                        new AggregateId(r.getAccommodationId()),
                        new DateRange<>(r.getStartDate(), r.getEndDate())
                )).toList();
        availability.tryToManualAssignTypeReservations(new AggregateId(command.getTypeReservationId()), reservations);
        repositoryPort.save(availability);
    }

    @Override
    @CommandHandlingAnnotation
    public void unassign(UnassignTypeReservationCommand command) {
        AccommodationTypeAvailability availability = repositoryPort.loadByTypeReservationId(new AggregateId(command.getAccommodationTypeReservationId()));
        AccommodationTypeReservation typeReservation = availability.getTypeReservationById(new AggregateId(command.getAccommodationTypeReservationId()));
        typeReservation.removeAllReservations();
        repositoryPort.save(availability);
    }
}
