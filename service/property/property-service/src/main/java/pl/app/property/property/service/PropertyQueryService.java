package pl.app.property.property.service;

import pl.app.common.service.QueryService;
import pl.app.property.organization.model.OrganizationEntity;
import pl.app.property.property.model.PropertyEntity;

import java.util.UUID;

public interface PropertyQueryService extends
        QueryService.SimpleFetchable.Full<UUID, PropertyEntity> {
}
