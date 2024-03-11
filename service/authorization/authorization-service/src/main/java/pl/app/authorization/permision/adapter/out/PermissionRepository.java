package pl.app.authorization.permision.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.app.authorization.permision.application.domain.Permission;
import pl.app.common.ddd.AggregateId;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PermissionRepository extends
        JpaRepository<Permission, AggregateId> {
    void deleteByAggregateId_AggregateId(UUID aggregateId);

    Optional<Permission> findByName(String name);
}
