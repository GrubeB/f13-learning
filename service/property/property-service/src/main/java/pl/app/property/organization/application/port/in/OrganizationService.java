package pl.app.property.organization.application.port.in;

import pl.app.common.service.CommandService;
import pl.app.property.organization.query.dto.OrganizationDto;
import pl.app.property.organization.application.domain.model.OrganizationEntity;

import java.util.UUID;

public interface OrganizationService extends
        CommandService.Creatable.DtoCreatable<UUID, OrganizationEntity, OrganizationDto, OrganizationDto>,
        CommandService.Updatable.DtoUpdatable<UUID, OrganizationEntity, OrganizationDto, OrganizationDto>,
        CommandService.Deletable.SimpleDeletable<UUID, OrganizationEntity> {
}
