package pl.app.learning.group.adapter.out.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.app.learning.group.application.domain.snapshot.GroupSnapshot;

import java.util.UUID;

@Repository
public interface GroupSnapshotRepository extends
        JpaRepository<GroupSnapshot, UUID> {
}
