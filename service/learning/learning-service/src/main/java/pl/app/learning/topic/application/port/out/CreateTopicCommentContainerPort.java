package pl.app.learning.topic.application.port.out;


import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.annotation.DomainRepositoryAnnotation;

@DomainRepositoryAnnotation
public interface CreateTopicCommentContainerPort {
    AggregateId create(AggregateId aggregateId);
}
