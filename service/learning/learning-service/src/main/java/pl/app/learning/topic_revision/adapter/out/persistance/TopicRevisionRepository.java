package pl.app.learning.topic_revision.adapter.out.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pl.app.learning.topic_revision.application.domain.TopicRevision;

import java.util.UUID;

@Repository
public interface TopicRevisionRepository extends
        JpaRepository<TopicRevision, UUID>,
        JpaSpecificationExecutor<TopicRevision> {
}
