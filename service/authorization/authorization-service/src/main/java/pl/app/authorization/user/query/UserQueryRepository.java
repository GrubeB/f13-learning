package pl.app.authorization.user.query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pl.app.authorization.user.query.model.UserQuery;

import java.util.UUID;

@Repository
public interface UserQueryRepository extends
        JpaRepository<UserQuery, UUID>,
        JpaSpecificationExecutor<UserQuery> {
}
