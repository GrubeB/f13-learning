package pl.app.authorization.role.query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pl.app.authorization.role.query.model.RoleQuery;

import java.util.UUID;

@Repository
public interface RoleQueryRepository extends
        JpaRepository<RoleQuery, UUID>,
        JpaSpecificationExecutor<RoleQuery> {
}
