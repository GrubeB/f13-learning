package pl.app.property.accommodation_type_details.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.app.cqrs.command.annotation.CommandHandlerAnnotation;
import pl.app.cqrs.command.annotation.CommandHandlingAnnotation;
import pl.app.property.accommodation_type_details.application.port.in.AccommodationTypeDetailsService;
import pl.app.property.accommodation_type_details.application.port.in.command.CreateAccommodationTypeDetailsCommand;
import pl.app.property.accommodation_type_details.query.dto.AccommodationTypeDetailsDto;

@CommandHandlerAnnotation
@Component
@RequiredArgsConstructor
public class AccommodationTypeDetailsCommandHandler {
    public final AccommodationTypeDetailsService service;

    @CommandHandlingAnnotation
    public AccommodationTypeDetailsDto handleCreateAccommodationTypeDetailsCommand(CreateAccommodationTypeDetailsCommand command) {
        return service.createFromDto(command);
    }
}
