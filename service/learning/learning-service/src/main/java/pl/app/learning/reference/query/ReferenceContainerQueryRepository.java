package pl.app.learning.reference.query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pl.app.learning.reference.query.model.ReferenceContainerQuery;

import java.util.UUID;

@Repository
public interface ReferenceContainerQueryRepository extends
        JpaRepository<ReferenceContainerQuery, UUID>,
        JpaSpecificationExecutor<ReferenceContainerQuery> {
}
