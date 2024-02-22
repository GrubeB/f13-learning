package pl.app.learning.chapter.service;


import java.util.UUID;

public interface ChapterSnapshotService {
    void revertSnapshot(UUID chapterId, Long snapshotNumber);
}
