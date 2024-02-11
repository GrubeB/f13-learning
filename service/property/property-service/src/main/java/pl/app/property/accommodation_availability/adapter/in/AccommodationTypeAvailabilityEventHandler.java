package pl.app.property.accommodation_availability.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.app.common.cqrs.event.annotation.EventHandlerAnnotation;
import pl.app.common.cqrs.event.annotation.EventHandlingAnnotation;
import pl.app.property.accommodation_availability.application.port.in.CreateAccommodationTypeAvailabilityUseCase;
import pl.app.property.accommodation_availability.application.port.in.command.CreateAccommodationTypeAvailabilityCommand;
import pl.app.property.accommodation_type.application.domain.event.AccommodationTypeCreatedEvent;

@EventHandlerAnnotation
@Component
@RequiredArgsConstructor
public class AccommodationTypeAvailabilityEventHandler {
    public final CreateAccommodationTypeAvailabilityUseCase createAccommodationTypeAvailabilityUseCase;

    @EventHandlingAnnotation
    public void handleAccommodationTypeCreatedEvent(AccommodationTypeCreatedEvent event) {
        var command = new CreateAccommodationTypeAvailabilityCommand(event.getPropertyId(), event.getAccommodationTypeId());
        createAccommodationTypeAvailabilityUseCase.create(command);
    }
}
