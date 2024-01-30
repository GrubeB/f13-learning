package pl.app.learning.group.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pl.app.learning.group.model.GroupEntity;

import java.util.UUID;

@Repository
public interface GroupRepository extends
        JpaRepository<GroupEntity, UUID>,
        JpaSpecificationExecutor<GroupEntity> {
}
