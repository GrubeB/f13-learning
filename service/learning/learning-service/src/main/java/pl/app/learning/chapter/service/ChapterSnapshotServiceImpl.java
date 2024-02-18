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
class ChapterSnapshotServiceImpl implements
        ChapterSnapshotService {
    private final ChapterRepository repository;
    private final ChapterSnapshotRepository chapterSnapshotRepository;
    private final ChapterMapper chapterMapper;

    @Override
    public void revertSnapshot(UUID chapterId, Long snapshotNumber) {
        ChapterEntity chapter = repository.findById(chapterId)
                .orElseThrow(RuntimeException::new);
        ChapterEntitySnapshot snapshot = chapter.getSnapshotBySnapshotNumber(snapshotNumber)
                .orElseThrow(RuntimeException::new);
        chapter.makeAndStoreSnapshot();
        chapter.revertSnapshot(snapshot);
        chapter = repository.save(chapter);
        List<ChapterEntitySnapshot> transientSnapshots = chapter.getTransientSnapshots();
        chapterSnapshotRepository.saveAll(transientSnapshots);
    }
}
