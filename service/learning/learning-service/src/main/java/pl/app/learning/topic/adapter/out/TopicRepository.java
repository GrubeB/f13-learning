package pl.app.learning.topic.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.app.common.ddd.AggregateId;
import pl.app.learning.topic.application.domain.Topic;

import java.util.UUID;

@Repository
public interface TopicRepository extends
        JpaRepository<Topic, AggregateId> {
    void deleteByAggregateId_AggregateId(UUID aggregateId);
}
