package pl.app.authorization.role.application.port.out;


import pl.app.authorization.role.application.domain.Role;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.annotation.DomainRepositoryAnnotation;

@DomainRepositoryAnnotation
public interface RoleDomainRepositoryPort {
    Role load(String roleName);

    void save(Role aggregate);

    void delete(Role aggregate);
}
