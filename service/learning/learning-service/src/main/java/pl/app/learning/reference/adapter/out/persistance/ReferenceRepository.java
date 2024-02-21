package pl.app.learning.reference.adapter.out.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.app.common.ddd.AggregateId;
import pl.app.learning.reference.application.domain.Reference;

import java.util.UUID;

@Repository
public interface ReferenceRepository extends
        JpaRepository<Reference, AggregateId> {
    void deleteByAggregateId_AggregateId(UUID aggregateId);
}
