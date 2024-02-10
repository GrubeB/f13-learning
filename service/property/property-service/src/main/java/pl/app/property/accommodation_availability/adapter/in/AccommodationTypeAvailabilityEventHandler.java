package pl.app.property.accommodation_availability.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.app.cqrs.event.annotation.EventHandlerAnnotation;
import pl.app.cqrs.event.annotation.EventHandlingAnnotation;
import pl.app.property.accommodation_availability.application.port.in.CreateAccommodationTypeAvailabilityUseCase;
import pl.app.property.accommodation_availability.application.port.in.command.CreateAccommodationTypeAvailabilityCommand;
import pl.app.property.accommodation_type.application.domain.event.AccommodationTypeCreatedEvent;
import pl.app.property.accommodation_type_details.application.port.in.AccommodationTypeDetailsService;
import pl.app.property.accommodation_type_details.application.port.in.command.CreateAccommodationTypeDetailsCommand;

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
