package pl.app.learning.group.application.port.out;


import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.annotation.DomainRepositoryAnnotation;
import pl.app.learning.group.application.domain.Group;

@DomainRepositoryAnnotation
public interface GroupDomainRepositoryPort {
    Group load(AggregateId aggregateId);

    void save(Group aggregate);

    void delete(AggregateId aggregateId);
}
