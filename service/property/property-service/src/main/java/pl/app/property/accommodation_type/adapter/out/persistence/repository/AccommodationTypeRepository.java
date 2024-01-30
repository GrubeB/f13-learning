package pl.app.property.accommodation_type.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pl.app.property.accommodation_type.adapter.out.persistence.model.AccommodationTypeEntity;

import java.util.UUID;

@Repository
public interface AccommodationTypeRepository extends
        JpaRepository<AccommodationTypeEntity, UUID>,
        JpaSpecificationExecutor<AccommodationTypeEntity> {
}
