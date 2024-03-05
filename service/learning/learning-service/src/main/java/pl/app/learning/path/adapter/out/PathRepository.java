package pl.app.learning.path.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.app.common.ddd.AggregateId;
import pl.app.learning.path.application.domain.Path;

import java.util.UUID;

@Repository
public interface PathRepository extends
        JpaRepository<Path, AggregateId> {
    void deleteByAggregateId_AggregateId(UUID aggregateId);
}
