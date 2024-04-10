package pl.app.learning.comment.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.app.common.ddd.AggregateId;
import pl.app.learning.comment.application.domain.CommentContainer;
import pl.app.learning.voting.application.domain.DomainObjectType;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CommandContainerRepository extends
        JpaRepository<CommentContainer, AggregateId> {
    Optional<CommentContainer> findByDomainObject_AggregateIdAndDomainObjectType(UUID aggregateId, DomainObjectType domainObjectType);

    Optional<CommentContainer> findByComments_EntityId(UUID entityId);

}
