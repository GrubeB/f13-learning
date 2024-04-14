package pl.app.learning.progress.query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pl.app.learning.progress.query.model.ProgressContainerQuery;

import java.util.UUID;

@Repository
public interface ProgressContainerQueryRepository extends
        JpaRepository<ProgressContainerQuery, UUID>,
        JpaSpecificationExecutor<ProgressContainerQuery> {
}
