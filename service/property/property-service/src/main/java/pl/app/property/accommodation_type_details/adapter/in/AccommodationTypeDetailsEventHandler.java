package pl.app.property.accommodation_type_details.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.app.common.cqrs.event.annotation.EventHandlerAnnotation;
import pl.app.common.cqrs.event.annotation.EventHandlingAnnotation;
import pl.app.property.accommodation_type.application.domain.event.AccommodationTypeCreatedEvent;
import pl.app.property.accommodation_type_details.application.port.in.AccommodationTypeDetailsService;
import pl.app.property.accommodation_type_details.application.port.in.command.CreateAccommodationTypeDetailsCommand;

@EventHandlerAnnotation
@Component
@RequiredArgsConstructor
public class AccommodationTypeDetailsEventHandler {
    public final AccommodationTypeDetailsService service;

    @EventHandlingAnnotation
    public void handleAccommodationTypeCreatedEvent(AccommodationTypeCreatedEvent event) {
        service.createFromDto(new CreateAccommodationTypeDetailsCommand(event.getAccommodationTypeId()));
    }
}
