package pl.app.learning.topic_revision.adapter.out.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.app.common.ddd.AggregateId;
import pl.app.learning.topic_revision.application.domain.TopicRevision;

import java.util.UUID;

@Repository
public interface TopicRevisionRepository extends
        JpaRepository<TopicRevision, AggregateId> {
    void deleteByAggregateId_AggregateId(UUID aggregateId);
}
