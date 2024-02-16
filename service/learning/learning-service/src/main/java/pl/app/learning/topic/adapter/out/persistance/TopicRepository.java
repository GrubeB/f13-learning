package pl.app.learning.topic.adapter.out.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pl.app.common.ddd.AggregateId;
import pl.app.learning.topic.application.domain.Topic;

@Repository
public interface TopicRepository extends
        JpaRepository<Topic, AggregateId>,
        JpaSpecificationExecutor<Topic> {
}
