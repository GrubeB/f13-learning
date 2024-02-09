package pl.app.property.property.query;

import pl.app.common.service.QueryService;
import pl.app.property.property.application.domain.model.PropertyEntity;

import java.util.UUID;

public interface PropertyQueryService extends
        QueryService.SimpleFetchable.Full<UUID, PropertyEntity> {
}
