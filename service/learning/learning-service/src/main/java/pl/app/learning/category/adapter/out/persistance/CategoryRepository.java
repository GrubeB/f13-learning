package pl.app.learning.category.adapter.out.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.app.common.ddd.AggregateId;
import pl.app.learning.category.application.domain.Category;

import java.util.UUID;

@Repository
public interface CategoryRepository extends
        JpaRepository<Category, AggregateId> {
    void deleteByAggregateId_AggregateId(UUID aggregateId);
}
