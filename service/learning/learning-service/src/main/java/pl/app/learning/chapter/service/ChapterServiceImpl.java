package pl.app.learning.chapter.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.learning.chapter.model.ChapterEntity;
import pl.app.learning.chapter.model.ChapterSnapshotEntity;
import pl.app.learning.chapter.persistance.ChapterRepository;
import pl.app.learning.chapter.persistance.ChapterVersionRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Getter
class ChapterServiceImpl implements
        ChapterService {
    private final ChapterRepository repository;
    private final ChapterVersionRepository chapterVersionRepository;

    @Override
    public void beforeUpdate(UUID uuid, ChapterEntity existingEntity, ChapterEntity newEntity) {
        existingEntity.makeAndStoreSnapshot();
    }

    @Override
    public ChapterEntity merge(ChapterEntity existingEntity, ChapterEntity newEntity) {
        existingEntity.setTopic(newEntity.getTopic());
        existingEntity.setIntroduction(newEntity.getIntroduction());
        return existingEntity;
    }

    @Override
    public void afterUpdate(UUID uuid, ChapterEntity savedEntity, ChapterEntity oldEntity) {
        List<ChapterSnapshotEntity> transientVersions = savedEntity.getTransientSnapshots();
        chapterVersionRepository.saveAll(transientVersions);
    }
}
