package pl.app.property.property.application.port.in;

import pl.app.common.service.CommandService;
import pl.app.property.property.application.domain.Property;
import pl.app.property.property.query.dto.PropertyDto;

import java.util.UUID;

public interface PropertyService extends
        CommandService.Creatable.DtoCreatable<UUID, Property, PropertyDto, PropertyDto>,
        CommandService.Updatable.DtoUpdatable<UUID, Property, PropertyDto, PropertyDto>,
        CommandService.Deletable.SimpleDeletable<UUID, Property> {
}
