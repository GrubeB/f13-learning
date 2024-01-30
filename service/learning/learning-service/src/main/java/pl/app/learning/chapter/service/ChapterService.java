package pl.app.learning.chapter.service;


import pl.app.common.service.CommandService;
import pl.app.learning.chapter.model.ChapterEntity;

import java.util.UUID;

public interface ChapterService extends
        CommandService.Creatable.SimpleCreatable<UUID, ChapterEntity>,
        CommandService.Updatable.SimpleUpdatable<UUID, ChapterEntity>,
        CommandService.Deletable.SimpleDeletable<UUID, ChapterEntity> {
}
