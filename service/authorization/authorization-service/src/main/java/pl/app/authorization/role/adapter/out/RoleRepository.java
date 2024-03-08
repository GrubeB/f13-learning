package pl.app.authorization.role.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.app.authorization.role.application.domain.Role;
import pl.app.common.ddd.AggregateId;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends
        JpaRepository<Role, AggregateId> {
    void deleteByAggregateId_AggregateId(UUID aggregateId);

    Optional<Role> findByName(String name);
}
