package pl.app.property.accommodation_type.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.cqrs.command.annotation.CommandHandlerAnnotation;
import pl.app.cqrs.command.annotation.CommandHandlingAnnotation;
import pl.app.ddd.AggregateId;
import pl.app.property.accommodation_type.application.domain.Accommodation;
import pl.app.property.accommodation_type.application.domain.AccommodationType;
import pl.app.property.accommodation_type.application.domain.AccommodationTypeFactory;
import pl.app.property.accommodation_type.application.port.in.AddAccommodationUseCase;
import pl.app.property.accommodation_type.application.port.in.CreateAccommodationTypeUseCase;
import pl.app.property.accommodation_type.application.port.in.RemoveAccommodationUseCase;
import pl.app.property.accommodation_type.application.port.in.command.AddAccommodationCommand;
import pl.app.property.accommodation_type.application.port.in.command.CreateAccommodationTypeCommand;
import pl.app.property.accommodation_type.application.port.in.command.RemoveAccommodationCommand;
import pl.app.property.accommodation_type.application.port.out.AccommodationTypeRepositoryPort;
import pl.app.property.accommodation_type.application.port.out.CreateAccommodationTypeAvailabilityPort;
import pl.app.property.accommodation_type.application.port.out.CreateAccommodationTypeDetailsPort;

import java.util.UUID;

@RequiredArgsConstructor
@Transactional
@CommandHandlerAnnotation
@Component
class AccommodationService implements
        CreateAccommodationTypeUseCase,
        AddAccommodationUseCase,
        RemoveAccommodationUseCase {
    private final AccommodationTypeRepositoryPort accommodationTypeRepositoryPort;
    private final CreateAccommodationTypeDetailsPort createAccommodationTypeDetailsPort;
    private final CreateAccommodationTypeAvailabilityPort createAccommodationTypeAvailabilityPort;
    private final AccommodationTypeFactory accommodationTypeFactory;

    @Override
    @CommandHandlingAnnotation
    public UUID createAccommodationType(CreateAccommodationTypeCommand command) {
        AccommodationType newAccommodationType = accommodationTypeFactory.createAccommodationType(new AggregateId(command.getPropertyId()));
        accommodationTypeRepositoryPort.save(newAccommodationType);
        createAccommodationTypeDetailsPort.create(newAccommodationType.getAggregateId(), command.getName(), command.getAbbreviation(),
                command.getDescription(), command.getGenderRoomType(), command.getRoomType());
        createAccommodationTypeAvailabilityPort.create(newAccommodationType.getAggregateId(), newAccommodationType.getPropertyId());
        return newAccommodationType.getAggregateId().getId();
    }

    @Override
    @CommandHandlingAnnotation
    public UUID addAccommodation(AddAccommodationCommand command) {
        AccommodationType accommodationType = accommodationTypeRepositoryPort.load(new AggregateId(command.getAccommodationTypeId()));
        Accommodation newAccommodation = new Accommodation(command.getName(), command.getDescription());
        accommodationType.addAccommodation(newAccommodation);
        accommodationTypeRepositoryPort.save(accommodationType);
        return newAccommodation.getId();
    }

    @Override
    @CommandHandlingAnnotation
    public void removeAccommodation(RemoveAccommodationCommand command) {
        AccommodationType accommodationType = accommodationTypeRepositoryPort.load(new AggregateId(command.getAccommodationTypeId()));
        accommodationType.removeAccommodationById(command.getAccommodationId());
        accommodationTypeRepositoryPort.save(accommodationType);
    }
}
