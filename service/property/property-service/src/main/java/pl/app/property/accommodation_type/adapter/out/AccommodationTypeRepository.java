package pl.app.property.accommodation_type.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pl.app.common.ddd.AggregateId;
import pl.app.property.accommodation_type.application.domain.AccommodationType;

import java.util.UUID;

@Repository
public interface AccommodationTypeRepository extends
        JpaRepository<AccommodationType, AggregateId>,
        JpaSpecificationExecutor<AccommodationType> {
    void deleteByAggregateId_AggregateId(UUID aggregateId);
}
