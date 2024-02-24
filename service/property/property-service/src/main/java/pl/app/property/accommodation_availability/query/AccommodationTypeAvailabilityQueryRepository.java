package pl.app.property.accommodation_availability.query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pl.app.property.accommodation_availability.query.model.AccommodationTypeAvailabilityQuery;

import java.util.UUID;

@Repository
interface AccommodationTypeAvailabilityQueryRepository extends
        JpaRepository<AccommodationTypeAvailabilityQuery, UUID>,
        JpaSpecificationExecutor<AccommodationTypeAvailabilityQuery> {
}
