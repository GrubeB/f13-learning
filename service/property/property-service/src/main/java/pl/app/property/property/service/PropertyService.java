package pl.app.property.property.service;

import pl.app.common.service.CommandService;
import pl.app.property.organization.dto.OrganizationDto;
import pl.app.property.organization.model.OrganizationEntity;
import pl.app.property.property.model.PropertyEntity;

import java.util.UUID;

public interface PropertyService extends
        CommandService.Creatable.SimpleCreatable<UUID, PropertyEntity>,
        CommandService.Updatable.SimpleUpdatable<UUID, PropertyEntity>,
        CommandService.Deletable.SimpleDeletable<UUID, PropertyEntity> {
}
