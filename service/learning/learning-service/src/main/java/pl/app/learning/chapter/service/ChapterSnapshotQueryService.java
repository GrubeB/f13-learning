package pl.app.learning.chapter.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.service.QueryService;
import pl.app.learning.chapter.model.ChapterEntitySnapshot;
import pl.app.learning.chapter.persistance.ChapterSnapshotRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
public class ChapterSnapshotQueryService implements
        QueryService.SimpleFetchable.Full<UUID, ChapterEntitySnapshot> {
    private final ChapterSnapshotRepository repository;
    private final ChapterSnapshotRepository specificationRepository;
    public List<ChapterEntitySnapshot> findByOwnerId(UUID ownerId){
        return repository.findByOwnerId(ownerId);
    }
}
