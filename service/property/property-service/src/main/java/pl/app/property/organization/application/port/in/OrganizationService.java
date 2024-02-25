package pl.app.property.organization.application.port.in;

import pl.app.common.service.CommandService;
import pl.app.property.organization.application.domain.Organization;
import pl.app.property.organization.query.dto.OrganizationDto;

import java.util.UUID;

public interface OrganizationService extends
        CommandService.Creatable.DtoCreatable<UUID, Organization, OrganizationDto, OrganizationDto>,
        CommandService.Updatable.DtoUpdatable<UUID, Organization, OrganizationDto, OrganizationDto>,
        CommandService.Deletable.SimpleDeletable<UUID, Organization> {
}
