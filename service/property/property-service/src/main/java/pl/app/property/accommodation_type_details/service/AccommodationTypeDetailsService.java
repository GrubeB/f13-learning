package pl.app.property.accommodation_type_details.service;

import pl.app.common.service.CommandService;
import pl.app.property.accommodation_type_details.dto.AccommodationTypeDetailsCreateDto;
import pl.app.property.accommodation_type_details.dto.AccommodationTypeDetailsDto;
import pl.app.property.accommodation_type_details.model.AccommodationTypeDetailsEntity;

import java.util.UUID;

public interface AccommodationTypeDetailsService extends
        CommandService.Creatable.DtoCreatable<UUID, AccommodationTypeDetailsEntity, AccommodationTypeDetailsCreateDto, AccommodationTypeDetailsDto>,
        CommandService.Updatable.DtoUpdatable<UUID, AccommodationTypeDetailsEntity, AccommodationTypeDetailsDto, AccommodationTypeDetailsDto>,
        CommandService.Deletable.SimpleDeletable<UUID, AccommodationTypeDetailsEntity> {
}
