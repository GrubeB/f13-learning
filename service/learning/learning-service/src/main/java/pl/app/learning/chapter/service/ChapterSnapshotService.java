package pl.app.learning.chapter.service;


import pl.app.common.service.CommandService;
import pl.app.learning.chapter.model.ChapterEntity;

import java.util.UUID;

public interface ChapterSnapshotService {
    void revertSnapshot(UUID chapterId, Long snapshotNumber);
}
