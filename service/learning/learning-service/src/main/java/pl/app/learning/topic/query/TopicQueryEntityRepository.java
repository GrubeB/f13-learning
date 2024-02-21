package pl.app.learning.topic.query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TopicQueryEntityRepository extends
        JpaRepository<TopicQuery, UUID>,
        JpaSpecificationExecutor<TopicQuery> {
}
