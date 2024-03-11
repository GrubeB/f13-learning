package pl.app.authorization.user.application.port.out;


import pl.app.authorization.user.application.domain.User;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.annotation.DomainRepositoryAnnotation;

@DomainRepositoryAnnotation
public interface UserDomainRepositoryPort {
    User load(AggregateId aggregateId);

    void save(User aggregate);
}
