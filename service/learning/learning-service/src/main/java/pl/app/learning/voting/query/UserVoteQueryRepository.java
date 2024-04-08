package pl.app.learning.voting.query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pl.app.learning.voting.query.model.UserVoteQuery;
import pl.app.learning.voting.query.model.VotingQuery;

import java.util.UUID;

@Repository
public interface UserVoteQueryRepository extends
        JpaRepository<UserVoteQuery, UUID>,
        JpaSpecificationExecutor<UserVoteQuery> {
}
