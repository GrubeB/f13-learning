package pl.app.learning.topic_revision.application.port.out;


import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.annotation.DomainRepositoryAnnotation;
import pl.app.learning.topic_revision.application.domain.TopicRevision;

@DomainRepositoryAnnotation
public interface TopicRevisionDomainRepositoryPort {
    TopicRevision load(AggregateId aggregateId);

    void save(TopicRevision aggregate);

    void delete(AggregateId aggregateId);
}
