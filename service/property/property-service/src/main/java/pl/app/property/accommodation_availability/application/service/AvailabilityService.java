package pl.app.property.accommodation_availability.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pl.app.cqrs.command.annotation.CommandHandlerAnnotation;
import pl.app.cqrs.command.annotation.CommandHandlingAnnotation;
import pl.app.ddd.AggregateId;
import pl.app.ddd.shared.DateRange;
import pl.app.property.accommodation_availability.application.domain.model.Accommodation;
import pl.app.property.accommodation_availability.application.domain.model.AccommodationTypeAvailability;
import pl.app.property.accommodation_availability.application.domain.model.AccommodationTypeAvailabilityFactory;
import pl.app.property.accommodation_availability.application.port.in.CreateAccommodationTypeAvailabilityUseCase;
import pl.app.property.accommodation_availability.application.port.in.IsAccommodationAvailableUseCase;
import pl.app.property.accommodation_availability.application.port.in.IsAccommodationTypeAvailableUseCase;
import pl.app.property.accommodation_availability.application.port.in.command.CreateAccommodationTypeAvailabilityCommand;
import pl.app.property.accommodation_availability.application.port.in.command.IsAccommodationAvailableCommand;
import pl.app.property.accommodation_availability.application.port.in.command.IsAccommodationTypeAvailableCommand;
import pl.app.property.accommodation_availability.application.port.out.AccommodationAvailabilityRepositoryPort;

import java.util.UUID;

@Component("accommodationAvailabilityService")
@RequiredArgsConstructor
@CommandHandlerAnnotation
class AvailabilityService implements
        IsAccommodationAvailableUseCase,
        IsAccommodationTypeAvailableUseCase,
        CreateAccommodationTypeAvailabilityUseCase {

    private final AccommodationAvailabilityRepositoryPort repositoryPort;
    private final AccommodationTypeAvailabilityFactory accommodationTypeAvailabilityFactory;


    @Override
    @CommandHandlingAnnotation
    public UUID create(CreateAccommodationTypeAvailabilityCommand command) {
        AccommodationTypeAvailability typeAvailability = accommodationTypeAvailabilityFactory.createAccommodationTypeAvailabilityFactory(command.getPropertyId(), command.getAccommodationTypeId());
        repositoryPort.save(typeAvailability);
        return typeAvailability.getAggregateId().getId();
    }

    @Override
    @CommandHandlingAnnotation
    public Boolean isAccommodationAvailable(IsAccommodationAvailableCommand command) {
        AccommodationTypeAvailability accommodationTypeAvailability = repositoryPort
                .loadByAccommodationId(command.getAccommodationId(), new DateRange<>(command.getStartDate(), command.getEndDate()));
        Accommodation accommodation = accommodationTypeAvailability.getAccommodationById(command.getAccommodationId());
        return accommodation.isAvailable(new DateRange<>(command.getStartDate(), command.getEndDate()));
    }

    @Override
    @CommandHandlingAnnotation
    public Boolean isAccommodationTypeAvailable(IsAccommodationTypeAvailableCommand command) {
        AccommodationTypeAvailability accommodationTypeAvailability = repositoryPort
                .loadByAccommodationTypeId(new AggregateId(command.getAccommodationTypeId()), new DateRange<>(command.getStartDate(), command.getEndDate()));
        return accommodationTypeAvailability.isAccommodationTypeAvailable(new DateRange<>(command.getStartDate(), command.getEndDate()),
                command.getNumberOfAccommodations());
    }
}
