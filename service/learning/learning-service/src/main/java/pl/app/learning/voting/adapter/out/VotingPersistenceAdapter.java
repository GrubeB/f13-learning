package pl.app.learning.voting.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.event.DelayedDomainEventPublisher;
import pl.app.common.ddd.event.DomainEventPublisherFactory;
import pl.app.learning.reference.application.domain.Reference;
import pl.app.learning.reference.application.domain.ReferenceException;
import pl.app.learning.reference.application.port.out.ReferenceDomainRepositoryPort;
import pl.app.learning.voting.application.domain.DomainObjectType;
import pl.app.learning.voting.application.domain.Voting;
import pl.app.learning.voting.application.domain.VotingException;
import pl.app.learning.voting.application.port.out.VotingDomainRepositoryPort;

@Component
@Transactional
@RequiredArgsConstructor
class VotingPersistenceAdapter implements
        VotingDomainRepositoryPort {
    private final VotingRepository repository;
    private final DomainEventPublisherFactory domainEventPublisherFactory;

    @Override
    public Voting load(AggregateId domainObject, DomainObjectType domainObjectType) {
        var entity = repository.findByDomainObject_AggregateIdAndDomainObjectType(domainObject.getId(), domainObjectType)
                .orElseThrow(() -> VotingException.NotFoundVotingException.fromDomainObject(domainObject.getId(), domainObjectType));
        entity.setEventPublisher(domainEventPublisherFactory.getEventPublisher());
        return entity;
    }

    @Override
    public void save(Voting aggregate) {
        repository.saveAndFlush(aggregate);
        if (aggregate.getEventPublisher() instanceof DelayedDomainEventPublisher publisher) {
            publisher.publishDelayedEvents();
        }
    }
}
