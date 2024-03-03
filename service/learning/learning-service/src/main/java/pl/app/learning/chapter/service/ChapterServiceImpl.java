package pl.app.learning.chapter.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.learning.chapter.model.Chapter;
import pl.app.learning.chapter.model.snapshot.ChapterSnapshot;
import pl.app.learning.chapter.persistance.ChapterRepository;
import pl.app.learning.chapter.persistance.ChapterSnapshotRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Getter
class ChapterServiceImpl implements
        ChapterService {
    private final ChapterRepository repository;
    private final ChapterSnapshotRepository chapterSnapshotRepository;
    private final ChapterMapper merger;

    @Override
    public void beforeUpdate(UUID uuid, Chapter existingEntity, Chapter newEntity) {
        existingEntity.makeAndStoreSnapshot();
    }

    @Override
    public void afterUpdate(UUID uuid, Chapter savedEntity, Chapter oldEntity) {
        List<ChapterSnapshot> transientSnapshots = savedEntity.getTransientSnapshots();
        chapterSnapshotRepository.saveAll(transientSnapshots);
    }
}
