package pl.app.learning.group.application.port.out;


import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.annotation.DomainRepositoryAnnotation;

@DomainRepositoryAnnotation
public interface CreateGroupReferenceContainerPort {
    AggregateId create(AggregateId aggregateId);
}
