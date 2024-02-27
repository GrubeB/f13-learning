package pl.app.common.search_criteria;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

 interface PropertyRepository extends
        JpaRepository<Property, UUID>,
        JpaSpecificationExecutor<Property> {
}
