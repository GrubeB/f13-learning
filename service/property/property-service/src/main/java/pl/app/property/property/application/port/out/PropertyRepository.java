package pl.app.property.property.application.port.out;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.app.property.property.application.domain.Property;

import java.util.List;
import java.util.UUID;

@Repository
public interface PropertyRepository extends
        JpaRepository<Property, UUID>,
        JpaSpecificationExecutor<Property> {
}
