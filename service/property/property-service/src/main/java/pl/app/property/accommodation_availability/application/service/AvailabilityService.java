package pl.app.property.accommodation_availability.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.app.common.cqrs.command.annotation.CommandHandlerAnnotation;
import pl.app.common.cqrs.command.annotation.CommandHandlingAnnotation;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.shared.DateRange;
import pl.app.property.accommodation_availability.application.domain.AccommodationAvailability;
import pl.app.property.accommodation_availability.application.domain.AccommodationTypeAvailability;
import pl.app.property.accommodation_availability.application.domain.AccommodationTypeAvailabilityFactory;
import pl.app.property.accommodation_availability.application.port.in.*;
import pl.app.property.accommodation_availability.application.port.in.command.*;
import pl.app.property.accommodation_availability.application.port.out.AccommodationAvailabilityRepositoryPort;

import java.util.UUID;

@Component("accommodationAvailabilityService")
@RequiredArgsConstructor
@CommandHandlerAnnotation
public class AvailabilityService implements
        AddAccommodationAvailabilityUseCase,
        RemoveAccommodationAvailabilityUseCase,
        IsAccommodationAvailableUseCase,
        IsAccommodationTypeAvailableUseCase,
        CreateAccommodationTypeAvailabilityUseCase {

    private final AccommodationAvailabilityRepositoryPort repository;
    private final AccommodationTypeAvailabilityFactory accommodationTypeAvailabilityFactory;


    @Override
    @CommandHandlingAnnotation
    public UUID create(CreateAccommodationTypeAvailabilityCommand command) {
        AccommodationTypeAvailability typeAvailability = accommodationTypeAvailabilityFactory
                .createAccommodationTypeAvailabilityFactory(new AggregateId(command.getPropertyId()), new AggregateId(command.getAccommodationTypeId()));
        repository.save(typeAvailability);
        return typeAvailability.getAggregateId().getId();
    }

    @Override
    @CommandHandlingAnnotation
    public Boolean isAccommodationAvailable(IsAccommodationAvailableCommand command) {
        AccommodationTypeAvailability accommodationTypeAvailability = repository
                .loadByAccommodationId(new AggregateId(command.getAccommodationId()), new DateRange<>(command.getStartDate(), command.getEndDate()));
        AccommodationAvailability accommodationAvailability = accommodationTypeAvailability.getAccommodationByIdOrThrow(new AggregateId(command.getAccommodationId()));
        return accommodationAvailability.isAvailable(new DateRange<>(command.getStartDate(), command.getEndDate()));
    }

    @Override
    @CommandHandlingAnnotation
    public Boolean isAccommodationTypeAvailable(IsAccommodationTypeAvailableCommand command) {
        AccommodationTypeAvailability accommodationTypeAvailability = repository
                .loadByAccommodationTypeId(new AggregateId(command.getAccommodationTypeId()), new DateRange<>(command.getStartDate(), command.getEndDate()));
        return accommodationTypeAvailability.isAccommodationTypeAvailable(new DateRange<>(command.getStartDate(), command.getEndDate()),
                command.getNumberOfAccommodations());
    }

    @Override
    @CommandHandlingAnnotation
    public void addAccommodationAvailability(AddAccommodationAvailabilityCommand command) {
        AccommodationTypeAvailability aggregate = repository.loadByAccommodationTypeId(new AggregateId(command.getAccommodationTypeId()));
        aggregate.addAccommodationAvailability(new AggregateId(command.getAccommodationId()));
        repository.save(aggregate);
    }

    @Override
    @CommandHandlingAnnotation
    public void removeAccommodationAvailability(RemoveAccommodationAvailabilityCommand command) {
        AccommodationTypeAvailability aggregate = repository.loadByAccommodationTypeId(new AggregateId(command.getAccommodationTypeId()));
        aggregate.removeAccommodationAvailability(new AggregateId(command.getAccommodationId()));
        repository.save(aggregate);
    }
}
