package pl.app.learning.chapter.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pl.app.learning.chapter.model.ChapterEntity;
import pl.app.learning.chapter.model.ChapterEntitySnapshot;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChapterSnapshotRepository extends
        JpaRepository<ChapterEntitySnapshot, UUID>,
        JpaSpecificationExecutor<ChapterEntitySnapshot> {
    List<ChapterEntitySnapshot> findBySnapshotOwnerId(UUID snapshotOwnerId);
    Optional<ChapterEntitySnapshot> findBySnapshotOwnerIdAndSnapshotNumber(UUID snapshotOwnerId, Long snapshotNumber);
}
