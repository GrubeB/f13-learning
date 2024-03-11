package pl.app.authorization.user.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.app.authorization.user.application.domain.User;
import pl.app.common.ddd.AggregateId;

@Repository
public interface UserRepository extends
        JpaRepository<User, AggregateId> {
}
