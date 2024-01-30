package pl.app.learning.group.service;


import pl.app.common.service.CommandService;
import pl.app.learning.group.model.GroupEntity;

import java.util.UUID;

public interface GroupService extends
        CommandService.Creatable.SimpleCreatable<UUID, GroupEntity>,
        CommandService.Updatable.SimpleUpdatable<UUID, GroupEntity>,
        CommandService.Deletable.SimpleDeletable<UUID, GroupEntity> {
}
