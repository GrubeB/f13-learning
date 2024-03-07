package pl.app.authorization.permision.application.port.out;


import pl.app.authorization.permision.application.domain.Permission;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.annotation.DomainRepositoryAnnotation;

@DomainRepositoryAnnotation
public interface PermissionDomainRepositoryPort {
    Permission load(AggregateId aggregateId);
    Permission load(String permissionName);

    void save(Permission aggregate);

    void delete(Permission aggregate);
}
