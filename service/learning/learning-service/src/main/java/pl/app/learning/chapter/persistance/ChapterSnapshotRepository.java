package pl.app.learning.chapter.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pl.app.learning.chapter.model.snapshot.ChapterSnapshot;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChapterSnapshotRepository extends
        JpaRepository<ChapterSnapshot, UUID>,
        JpaSpecificationExecutor<ChapterSnapshot> {
    List<ChapterSnapshot> findBySnapshotOwnerId(UUID snapshotOwnerId);

    Optional<ChapterSnapshot> findBySnapshotOwnerIdAndSnapshotNumber(UUID snapshotOwnerId, Long snapshotNumber);
}
