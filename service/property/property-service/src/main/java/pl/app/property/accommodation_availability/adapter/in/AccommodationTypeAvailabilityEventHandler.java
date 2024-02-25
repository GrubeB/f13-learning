package pl.app.property.accommodation_availability.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.app.common.cqrs.command.gateway.CommandGateway;
import pl.app.common.cqrs.event.annotation.EventHandlerAnnotation;
import pl.app.common.cqrs.event.annotation.EventHandlingAnnotation;
import pl.app.property.accommodation_availability.application.port.in.command.AddAccommodationAvailabilityCommand;
import pl.app.property.accommodation_availability.application.port.in.command.CreateAccommodationTypeAvailabilityCommand;
import pl.app.property.accommodation_availability.application.port.in.command.RemoveAccommodationAvailabilityCommand;
import pl.app.property.accommodation_type.application.domain.event.AccommodationCreatedEvent;
import pl.app.property.accommodation_type.application.domain.event.AccommodationRemovedEvent;
import pl.app.property.accommodation_type.application.domain.event.AccommodationTypeCreatedEvent;

@EventHandlerAnnotation
@Component
@RequiredArgsConstructor
public class AccommodationTypeAvailabilityEventHandler {
    public final CommandGateway gateway;

    @EventHandlingAnnotation
    public void handle(AccommodationTypeCreatedEvent event) {
        var command = new CreateAccommodationTypeAvailabilityCommand(event.getPropertyId(), event.getAccommodationTypeId());
        gateway.send(command);
    }

    @EventHandlingAnnotation
    public void handle(AccommodationCreatedEvent event) {
        var command = new AddAccommodationAvailabilityCommand(event.getAccommodationTypeId(), event.getAccommodationId());
        gateway.send(command);

    }

    @EventHandlingAnnotation
    public void handle(AccommodationRemovedEvent event) {
        var command = new RemoveAccommodationAvailabilityCommand(event.getAccommodationTypeId(), event.getAccommodationId());
        gateway.send(command);
    }
}
