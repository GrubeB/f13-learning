package pl.app.property.accommodation_type.query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pl.app.property.accommodation_type.query.model.AccommodationTypeQuery;

import java.util.UUID;

@Repository
interface AccommodationTypeQueryRepository extends
        JpaRepository<AccommodationTypeQuery, UUID>,
        JpaSpecificationExecutor<AccommodationTypeQuery> {
}
