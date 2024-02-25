package pl.app.property.accommodation_type.query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pl.app.property.accommodation_type.query.model.AccommodationQuery;

import java.util.UUID;

@Repository
interface AccommodationQueryRepository extends
        JpaRepository<AccommodationQuery, UUID>,
        JpaSpecificationExecutor<AccommodationQuery> {
}
