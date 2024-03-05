package pl.app.learning.path.query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pl.app.learning.path.query.model.PathQuery;

import java.util.UUID;

@Repository
public interface PathQueryRepository extends
        JpaRepository<PathQuery, UUID>,
        JpaSpecificationExecutor<PathQuery> {
}
