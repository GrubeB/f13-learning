package pl.app.authorization.permision.query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pl.app.authorization.permision.query.model.PermissionQuery;

import java.util.UUID;

@Repository
public interface PermissionQueryRepository extends
        JpaRepository<PermissionQuery, UUID>,
        JpaSpecificationExecutor<PermissionQuery> {
}
