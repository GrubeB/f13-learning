package pl.app.learning.category.query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryQueryRepository extends
        JpaRepository<CategoryQuery, UUID>,
        JpaSpecificationExecutor<CategoryQuery> {
}
