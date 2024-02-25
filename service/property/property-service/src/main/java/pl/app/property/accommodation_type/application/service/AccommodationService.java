package pl.app.property.accommodation_type.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.cqrs.command.annotation.CommandHandlerAnnotation;
import pl.app.common.cqrs.command.annotation.CommandHandlingAnnotation;
import pl.app.common.ddd.AggregateId;
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

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Transactional
@CommandHandlerAnnotation
public class AccommodationService implements
        CreateAccommodationTypeUseCase,
        AddAccommodationUseCase,
        RemoveAccommodationUseCase {
    private final AccommodationTypeRepositoryPort accommodationTypeRepositoryPort;
    private final AccommodationTypeFactory accommodationTypeFactory;

    @Override
    @CommandHandlingAnnotation
    public UUID createAccommodationType(CreateAccommodationTypeCommand command) {
        AccommodationType newAggregate = accommodationTypeFactory.createAccommodationType(new AggregateId(command.getPropertyId()));
        accommodationTypeRepositoryPort.save(newAggregate);
        return newAggregate.getAggregateId().getId();
    }

    @Override
    @CommandHandlingAnnotation
    public UUID addAccommodation(AddAccommodationCommand command) {
        AccommodationType aggregate = accommodationTypeRepositoryPort.load(new AggregateId(command.getAccommodationTypeId()));
        Accommodation newAccommodation = new Accommodation(aggregate, command.getName(), command.getDescription());
        aggregate.addAccommodation(newAccommodation);
        accommodationTypeRepositoryPort.save(aggregate);
        return newAccommodation.getId();
    }

    @Override
    @CommandHandlingAnnotation
    public void removeAccommodation(RemoveAccommodationCommand command) {
        AccommodationType aggregate = accommodationTypeRepositoryPort.load(new AggregateId(command.getAccommodationTypeId()));
        aggregate.removeAccommodationById(new AggregateId(command.getAccommodationId()));
        accommodationTypeRepositoryPort.save(aggregate);
    }
}
