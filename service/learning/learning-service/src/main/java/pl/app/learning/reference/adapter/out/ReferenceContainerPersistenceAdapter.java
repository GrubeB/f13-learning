package pl.app.learning.reference.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.event.DelayedDomainEventPublisher;
import pl.app.common.ddd.event.DomainEventPublisherFactory;
import pl.app.learning.reference.application.domain.Reference;
import pl.app.learning.reference.application.domain.ReferenceContainer;
import pl.app.learning.reference.application.domain.ReferenceException;
import pl.app.learning.reference.application.port.out.ReferenceContainerDomainRepositoryPort;
import pl.app.learning.reference.application.port.out.ReferenceDomainRepositoryPort;
import pl.app.learning.voting.application.domain.DomainObjectType;

@Component
@Transactional
@RequiredArgsConstructor
class ReferenceContainerPersistenceAdapter implements
        ReferenceContainerDomainRepositoryPort {
    private final ReferenceContainerRepository repository;
    private final DomainEventPublisherFactory domainEventPublisherFactory;

    @Override
    public ReferenceContainer load(AggregateId domainObject, DomainObjectType domainObjectType) {
        var entity = repository.findByDomainObject_AggregateIdAndDomainObjectType(domainObject.getId(), domainObjectType)
                .orElseThrow(() -> ReferenceException.NotFoundReferenceContainerException.fromDomainObject(domainObject.getId(), domainObjectType));
        entity.setEventPublisher(domainEventPublisherFactory.getEventPublisher());
        return entity;
    }

    @Override
    public ReferenceContainer loadByReference(AggregateId reference) {
        var entity = repository.findByReferences_AggregateId_AggregateId(reference.getId())
                .orElseThrow(() -> ReferenceException.NotFoundReferenceException.fromId(reference.getId()));
        entity.setEventPublisher(domainEventPublisherFactory.getEventPublisher());
        return entity;
    }

    @Override
    public void save(ReferenceContainer aggregate) {
        repository.saveAndFlush(aggregate);
        if (aggregate.getEventPublisher() instanceof DelayedDomainEventPublisher publisher) {
            publisher.publishDelayedEvents();
        }
    }
}
