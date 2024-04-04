package pl.app.learning.voting.query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pl.app.learning.voting.query.model.VotingQuery;

import java.util.UUID;

@Repository
public interface VotingQueryRepository extends
        JpaRepository<VotingQuery, UUID>,
        JpaSpecificationExecutor<VotingQuery> {
}
