package pl.app.learning.group.service;


import pl.app.common.service.QueryService;
import pl.app.learning.group.model.GroupEntity;

import java.util.UUID;

public interface GroupQueryService extends
        QueryService.SimpleFetchable.Full<UUID, GroupEntity> {
}
