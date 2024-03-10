package pl.app.authorization.permision.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.authorization.permision.application.domain.Permission;
import pl.app.authorization.permision.application.domain.PermissionException;
import pl.app.authorization.permision.application.port.out.PermissionDomainRepositoryPort;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.event.DelayedDomainEventPublisher;
import pl.app.common.ddd.event.DomainEventPublisherFactory;

@Component
@Transactional
@RequiredArgsConstructor
class PermissionPersistenceAdapter implements
        PermissionDomainRepositoryPort {
    private final PermissionRepository repository;
    private final DomainEventPublisherFactory domainEventPublisherFactory;

    @Override
    public Permission load(String permissionName) {
        var aggregate = repository.findByName(permissionName)
                .orElseThrow(() -> PermissionException.NotFoundPermissionException.fromName(permissionName));
        aggregate.setEventPublisher(domainEventPublisherFactory.getEventPublisher());
        return aggregate;
    }

    @Override
    public Permission load(AggregateId aggregateId) {
        var aggregate = repository.findById(aggregateId)
                .orElseThrow(() -> PermissionException.NotFoundPermissionException.fromId(aggregateId.getId()));
        aggregate.setEventPublisher(domainEventPublisherFactory.getEventPublisher());
        return aggregate;
    }

    @Override
    public void save(Permission aggregate) {
        repository.saveAndFlush(aggregate);
        if (aggregate.getEventPublisher() instanceof DelayedDomainEventPublisher publisher) {
            publisher.publishDelayedEvents();
        }
    }

    @Override
    public void delete(Permission aggregate) {
        if (aggregate.getEventPublisher() instanceof DelayedDomainEventPublisher publisher) {
            publisher.publishDelayedEvents();
        }
        repository.deleteByAggregateId_AggregateId(aggregate.getId());
    }
}
