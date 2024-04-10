package pl.app.learning.group.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.app.common.ddd.AggregateId;
import pl.app.learning.group.application.domain.Group;

import java.util.UUID;

@Repository
public interface GroupRepository extends
        JpaRepository<Group, AggregateId> {
    void deleteByAggregateId_AggregateId(UUID aggregateId);
}
