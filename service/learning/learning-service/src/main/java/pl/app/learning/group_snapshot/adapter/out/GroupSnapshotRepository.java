package pl.app.learning.group_snapshot.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.app.learning.group_snapshot.application.domain.GroupSnapshot;

import java.util.UUID;

@Repository
public interface GroupSnapshotRepository extends
        JpaRepository<GroupSnapshot, UUID> {
}
