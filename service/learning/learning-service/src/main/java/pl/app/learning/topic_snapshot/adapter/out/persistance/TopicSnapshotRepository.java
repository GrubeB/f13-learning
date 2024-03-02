package pl.app.learning.topic_snapshot.adapter.out.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.app.learning.topic_snapshot.domain.model.TopicSnapshot;

import java.util.UUID;

@Repository
public interface TopicSnapshotRepository extends
        JpaRepository<TopicSnapshot, UUID> {
}
