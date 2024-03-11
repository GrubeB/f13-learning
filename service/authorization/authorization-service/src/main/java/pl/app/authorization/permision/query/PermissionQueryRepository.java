package pl.app.authorization.permision.query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.app.authorization.permision.query.model.PermissionQuery;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PermissionQueryRepository extends
        JpaRepository<PermissionQuery, UUID>,
        JpaSpecificationExecutor<PermissionQuery> {
    @Query("select p from PermissionQuery p where p.name = ?1")
    Optional<PermissionQuery> findByName(String name);
}
