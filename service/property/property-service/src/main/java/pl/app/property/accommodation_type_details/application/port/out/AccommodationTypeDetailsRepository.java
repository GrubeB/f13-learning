package pl.app.property.accommodation_type_details.application.port.out;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pl.app.property.accommodation_type_details.application.domain.model.AccommodationTypeDetailsEntity;

import java.util.UUID;

@Repository
public interface AccommodationTypeDetailsRepository extends
        JpaRepository<AccommodationTypeDetailsEntity, UUID>,
        JpaSpecificationExecutor<AccommodationTypeDetailsEntity> {
}
