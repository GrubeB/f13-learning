package pl.app.property.organization.query;

import pl.app.common.service.QueryService;
import pl.app.property.organization.application.domain.Organization;

import java.util.UUID;

public interface OrganizationQueryService extends
        QueryService.Full<UUID, Organization> {
}
