package pl.app.learning.progress.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.event.DelayedDomainEventPublisher;
import pl.app.common.ddd.event.DomainEventPublisherFactory;
import pl.app.learning.progress.application.domain.ProgressContainer;
import pl.app.learning.progress.application.domain.ProgressException;
import pl.app.learning.progress.application.port.out.ProgressContainerRepositoryPort;
import pl.app.learning.voting.application.domain.DomainObjectType;

@Component
@Transactional
@RequiredArgsConstructor
class ProgressContainerPersistenceAdapter implements
        ProgressContainerRepositoryPort {
    private final ProgressContainerRepository repository;
    private final DomainEventPublisherFactory domainEventPublisherFactory;

    @Override
    public ProgressContainer load(AggregateId domainObject, DomainObjectType domainObjectType) {
        var entity = repository.findByDomainObject_AggregateIdAndDomainObjectType(domainObject.getId(), domainObjectType)
                .orElseThrow(() -> ProgressException.NotFoundProgressException.fromDomainObject(domainObject.getId(), domainObjectType));
        entity.setEventPublisher(domainEventPublisherFactory.getEventPublisher());
        return entity;
    }

    @Override
    public void save(ProgressContainer aggregate) {
        repository.saveAndFlush(aggregate);
        if (aggregate.getEventPublisher() instanceof DelayedDomainEventPublisher publisher) {
            publisher.publishDelayedEvents();
        }
    }
}
