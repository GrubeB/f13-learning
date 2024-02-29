package pl.app.learning.group_revision.adapter.out.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pl.app.learning.group_revision.application.domain.GroupRevision;

import java.util.UUID;

@Repository
public interface GroupRevisionRepository extends
        JpaRepository<GroupRevision, UUID>,
        JpaSpecificationExecutor<GroupRevision> {
}
