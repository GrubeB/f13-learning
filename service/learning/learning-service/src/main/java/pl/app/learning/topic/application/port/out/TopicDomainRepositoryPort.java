package pl.app.learning.topic.application.port.out;


import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.annotation.DomainRepositoryAnnotation;
import pl.app.learning.topic.application.domain.Topic;

@DomainRepositoryAnnotation
public interface TopicDomainRepositoryPort {
    Topic load(AggregateId aggregateId);

    void save(Topic aggregate);
}
