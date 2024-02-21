package pl.app.learning.topic.adapter.out.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.app.common.ddd.AggregateId;
import pl.app.learning.topic.application.domain.Topic;
import pl.app.learning.topic.application.domain.TopicSnapshot;

import java.util.UUID;

@Repository
public interface TopicSnapshotRepository extends
        JpaRepository<TopicSnapshot, UUID> {
}
