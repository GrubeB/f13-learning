package pl.app.learning.topic_revision.adapter.out.persistance;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.event.DomainEventPublisherFactory;
import pl.app.learning.topic_revision.application.domain.TopicRevision;
import pl.app.learning.topic_revision.application.domain.TopicRevisionException;
import pl.app.learning.topic_revision.application.port.out.TopicRevisionDomainRepositoryPort;

@Component
@Transactional
@RequiredArgsConstructor
class TopicRevisionPersistenceAdapter implements
        TopicRevisionDomainRepositoryPort {
    private final TopicRevisionRepository repository;

    @Override
    public TopicRevision load(AggregateId aggregateId) {
        return repository.findById(aggregateId.getId())
                .orElseThrow(() -> TopicRevisionException.NotFoundTopicRevisionException.fromId(aggregateId.getId()));
    }

    @Override
    public void save(TopicRevision aggregate) {
        repository.saveAndFlush(aggregate);
    }

    @Override
    public void delete(AggregateId aggregateId) {
        repository.deleteById(aggregateId.getId());
    }
}
