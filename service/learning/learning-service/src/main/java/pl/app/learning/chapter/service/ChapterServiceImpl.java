package pl.app.learning.chapter.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.learning.chapter.model.ChapterEntity;
import pl.app.learning.chapter.model.ChapterEntitySnapshot;
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
    private final ChapterMapper chapterMapper;

    @Override
    public void beforeUpdate(UUID uuid, ChapterEntity existingEntity, ChapterEntity newEntity) {
        existingEntity.makeAndStoreSnapshot();
    }

    @Override
    public ChapterEntity merge(ChapterEntity existingEntity, ChapterEntity newEntity) {
            return chapterMapper.merge(existingEntity, newEntity);
    }

    @Override
    public void afterUpdate(UUID uuid, ChapterEntity savedEntity, ChapterEntity oldEntity) {
        List<ChapterEntitySnapshot> transientVersions = savedEntity.getTransientSnapshots();
        chapterSnapshotRepository.saveAll(transientVersions);
    }
}
