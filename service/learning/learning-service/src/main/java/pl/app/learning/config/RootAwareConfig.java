package pl.app.learning.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaUpdate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.AuditorAware;
import pl.app.common.audit.Audit;
import pl.app.common.aware.RootAwareServiceOperation;

import java.time.Instant;

@Configuration
@Import(pl.app.common.aware.RootAwareConfig.class)
public class RootAwareConfig {
    @Bean
    public RootAwareServiceOperation rootAwareServiceOperation(EntityManager entityManager, AuditorAware<?> auditorAware) {
        return new RootAwareServiceOperation() {
            @Override
            public void updateOperation(Object root) {
                if (root instanceof Audit) {
                    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                    CriteriaUpdate<?> criteriaUpdate = cb.createCriteriaUpdate(root.getClass());
                    criteriaUpdate.set("lastModifiedDate", Instant.now());
                    criteriaUpdate.set("lastModifiedBy", auditorAware.getCurrentAuditor().orElse(null));
                    entityManager.createQuery(criteriaUpdate).executeUpdate();
                }
            }
        };
    }
}
