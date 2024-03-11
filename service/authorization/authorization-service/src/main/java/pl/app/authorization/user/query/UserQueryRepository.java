package pl.app.authorization.user.query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.app.authorization.user.query.model.UserQuery;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserQueryRepository extends
        JpaRepository<UserQuery, UUID>,
        JpaSpecificationExecutor<UserQuery> {
    @Query("select u from UserQuery u where u.email = ?1")
    Optional<UserQuery> findByEmail(String email);

}
