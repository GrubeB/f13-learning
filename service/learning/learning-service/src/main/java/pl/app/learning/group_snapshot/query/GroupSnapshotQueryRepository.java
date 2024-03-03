package pl.app.learning.group_snapshot.query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pl.app.learning.group_snapshot.query.model.GroupSnapshotQuery;

import java.util.UUID;

@Repository
public interface GroupSnapshotQueryRepository extends
        JpaRepository<GroupSnapshotQuery, UUID>,
        JpaSpecificationExecutor<GroupSnapshotQuery> {
}
