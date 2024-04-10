package pl.app.learning.reference.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.app.common.ddd.AggregateId;
import pl.app.learning.comment.application.domain.CommentContainer;
import pl.app.learning.reference.application.domain.Reference;
import pl.app.learning.reference.application.domain.ReferenceContainer;
import pl.app.learning.voting.application.domain.DomainObjectType;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReferenceContainerRepository extends
        JpaRepository<ReferenceContainer, AggregateId> {
    Optional<ReferenceContainer> findByDomainObject_AggregateIdAndDomainObjectType(UUID aggregateId, DomainObjectType domainObjectType);

    Optional<ReferenceContainer> findByReferences_AggregateId_AggregateId(UUID aggregateId);
}
