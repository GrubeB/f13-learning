package pl.app.learning.path.application.port.out;


import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.annotation.DomainRepositoryAnnotation;

@DomainRepositoryAnnotation
public interface CreatePathCommentContainerPort {
    AggregateId create(AggregateId aggregateId);
}
