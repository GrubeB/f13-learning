package pl.app.property.accommodation_type_details.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pl.app.property.accommodation_type_details.model.AccommodationTypeDetailsEntity;

import java.util.UUID;

@Repository
public interface AccommodationTypeDetailsRepository extends
        JpaRepository<AccommodationTypeDetailsEntity, UUID>,
        JpaSpecificationExecutor<AccommodationTypeDetailsEntity> {
}
