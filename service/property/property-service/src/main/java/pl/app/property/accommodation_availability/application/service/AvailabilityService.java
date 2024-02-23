package pl.app.property.accommodation_availability.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.shared.DateRange;
import pl.app.property.accommodation_availability.application.domain.model.AccommodationAvailability;
import pl.app.property.accommodation_availability.application.domain.model.AccommodationTypeAvailability;
import pl.app.property.accommodation_availability.application.domain.AccommodationTypeAvailabilityFactory;
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
class AvailabilityService implements
        IsAccommodationAvailableUseCase,
        IsAccommodationTypeAvailableUseCase,
        CreateAccommodationTypeAvailabilityUseCase {

    private final AccommodationAvailabilityRepositoryPort repositoryPort;
    private final AccommodationTypeAvailabilityFactory accommodationTypeAvailabilityFactory;


    @Override
    public UUID create(CreateAccommodationTypeAvailabilityCommand command) {
        AccommodationTypeAvailability typeAvailability = accommodationTypeAvailabilityFactory
                .createAccommodationTypeAvailabilityFactory(new AggregateId(command.getPropertyId()), new AggregateId(command.getAccommodationTypeId()));
        repositoryPort.save(typeAvailability);
        return typeAvailability.getAggregateId().getId();
    }

    @Override
    public Boolean isAccommodationAvailable(IsAccommodationAvailableCommand command) {
        AccommodationTypeAvailability accommodationTypeAvailability = repositoryPort
                .loadByAccommodationId(command.getAccommodationId(), new DateRange<>(command.getStartDate(), command.getEndDate()));
        AccommodationAvailability accommodationAvailability = accommodationTypeAvailability.getAccommodationById(command.getAccommodationId());
        return accommodationAvailability.isAvailable(new DateRange<>(command.getStartDate(), command.getEndDate()));
    }

    @Override
    public Boolean isAccommodationTypeAvailable(IsAccommodationTypeAvailableCommand command) {
        AccommodationTypeAvailability accommodationTypeAvailability = repositoryPort
                .loadByAccommodationTypeId(new AggregateId(command.getAccommodationTypeId()), new DateRange<>(command.getStartDate(), command.getEndDate()));
        return accommodationTypeAvailability.isAccommodationTypeAvailable(new DateRange<>(command.getStartDate(), command.getEndDate()),
                command.getNumberOfAccommodations());
    }
}
