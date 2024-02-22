package pl.app.learning.chapter.service;


import pl.app.common.service.CommandService;
import pl.app.learning.chapter.model.Chapter;

import java.util.UUID;

public interface ChapterService extends
        CommandService.Creatable.SimpleCreatable<UUID, Chapter>,
        CommandService.Updatable.SimpleUpdatable<UUID, Chapter>,
        CommandService.Deletable.SimpleDeletable<UUID, Chapter> {
}
