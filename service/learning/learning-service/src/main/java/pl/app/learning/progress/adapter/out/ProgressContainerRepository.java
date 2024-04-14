package pl.app.learning.progress.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.app.common.ddd.AggregateId;
import pl.app.learning.progress.application.domain.ProgressContainer;
import pl.app.learning.voting.application.domain.DomainObjectType;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProgressContainerRepository extends
        JpaRepository<ProgressContainer, AggregateId> {
    Optional<ProgressContainer> findByDomainObject_AggregateIdAndDomainObjectType(UUID aggregateId, DomainObjectType domainObjectType);
}
