package pl.app.learning.group.query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pl.app.learning.group.query.model.GroupQuery;

import java.util.UUID;

@Repository
public interface GroupQueryRepository extends
        JpaRepository<GroupQuery, UUID>,
        JpaSpecificationExecutor<GroupQuery> {
}
