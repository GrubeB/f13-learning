package pl.app.property.organization.application.port.out;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pl.app.property.organization.application.domain.Organization;

import java.util.UUID;

@Repository
public interface OrganizationRepository extends
        JpaRepository<Organization, UUID>,
        JpaSpecificationExecutor<Organization> {
}
