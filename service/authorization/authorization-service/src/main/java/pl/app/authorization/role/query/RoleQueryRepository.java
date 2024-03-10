package pl.app.authorization.role.query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.app.authorization.role.query.model.RoleQuery;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleQueryRepository extends
        JpaRepository<RoleQuery, UUID>,
        JpaSpecificationExecutor<RoleQuery> {
    @Query("select p from RoleQuery p where p.name = ?1")
    Optional<RoleQuery> findByName(String name);
}
