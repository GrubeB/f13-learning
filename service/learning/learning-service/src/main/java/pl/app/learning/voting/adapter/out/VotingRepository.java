package pl.app.learning.voting.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.app.common.ddd.AggregateId;
import pl.app.learning.reference.application.domain.Reference;
import pl.app.learning.voting.application.domain.DomainObjectType;
import pl.app.learning.voting.application.domain.Voting;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VotingRepository extends
        JpaRepository<Voting, AggregateId> {
    Optional<Voting> findByDomainObject_AggregateIdAndDomainObjectType(UUID aggregateId, DomainObjectType domainObjectType);
}
