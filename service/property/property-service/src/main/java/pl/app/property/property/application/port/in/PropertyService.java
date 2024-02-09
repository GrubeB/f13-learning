package pl.app.property.property.application.port.in;

import pl.app.common.service.CommandService;
import pl.app.property.property.application.domain.model.PropertyEntity;

import java.util.UUID;

public interface PropertyService extends
        CommandService.Creatable.SimpleCreatable<UUID, PropertyEntity>,
        CommandService.Updatable.SimpleUpdatable<UUID, PropertyEntity>,
        CommandService.Deletable.SimpleDeletable<UUID, PropertyEntity> {
}
