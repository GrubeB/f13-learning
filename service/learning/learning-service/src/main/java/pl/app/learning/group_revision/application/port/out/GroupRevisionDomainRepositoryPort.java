package pl.app.learning.group_revision.application.port.out;


import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.annotation.DomainRepositoryAnnotation;
import pl.app.learning.group_revision.application.domain.GroupRevision;

@DomainRepositoryAnnotation
public interface GroupRevisionDomainRepositoryPort {
    GroupRevision load(AggregateId aggregateId);

    void save(GroupRevision aggregate);

    void delete(AggregateId aggregateId);
}
