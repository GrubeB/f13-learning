package pl.app.property.accommodation_type_details.application.port.in;

import pl.app.common.service.CommandService;
import pl.app.property.accommodation_type_details.application.port.in.command.CreateAccommodationTypeDetailsCommand;
import pl.app.property.accommodation_type_details.application.port.in.command.UpdateAccommodationTypeDetailsCommand;
import pl.app.property.accommodation_type_details.query.dto.AccommodationTypeDetailsDto;
import pl.app.property.accommodation_type_details.application.domain.model.AccommodationTypeDetails;

import java.util.UUID;

public interface AccommodationTypeDetailsService extends
        CommandService.Creatable.DtoCreatable<UUID, AccommodationTypeDetails, CreateAccommodationTypeDetailsCommand, AccommodationTypeDetailsDto>,
        CommandService.Updatable.DtoUpdatable<UUID, AccommodationTypeDetails, UpdateAccommodationTypeDetailsCommand, AccommodationTypeDetailsDto>,
        CommandService.Deletable.SimpleDeletable<UUID, AccommodationTypeDetails> {
}
