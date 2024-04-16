package pl.app.learning.progress.query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pl.app.learning.progress.query.model.ProgressQuery;

import java.util.UUID;

@Repository
public interface ProgressQueryRepository extends
        JpaRepository<ProgressQuery, UUID>,
        JpaSpecificationExecutor<ProgressQuery> {
}
