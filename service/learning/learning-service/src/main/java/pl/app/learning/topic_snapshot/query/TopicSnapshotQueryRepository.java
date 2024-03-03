package pl.app.learning.topic_snapshot.query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pl.app.learning.topic_snapshot.query.model.TopicSnapshotQuery;

import java.util.UUID;

@Repository
public interface TopicSnapshotQueryRepository extends
        JpaRepository<TopicSnapshotQuery, UUID>,
        JpaSpecificationExecutor<TopicSnapshotQuery> {
}
