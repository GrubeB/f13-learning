package pl.app.authorization.role.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.authorization.role.application.domain.Role;
import pl.app.authorization.role.application.domain.RoleException;
import pl.app.authorization.role.application.port.out.RoleDomainRepositoryPort;
import pl.app.common.ddd.event.DelayedDomainEventPublisher;
import pl.app.common.ddd.event.DomainEventPublisherFactory;

@Component
@Transactional
@RequiredArgsConstructor
class RolePersistenceAdapter implements
        RoleDomainRepositoryPort {
    private final RoleRepository repository;
    private final DomainEventPublisherFactory domainEventPublisherFactory;

    @Override
    public Role load(String permissionName) {
        var aggregate = repository.findByName(permissionName)
                .orElseThrow(() -> RoleException.NotFoundRoleException.fromName(permissionName));
        aggregate.setEventPublisher(domainEventPublisherFactory.getEventPublisher());
        return aggregate;
    }

    @Override
    public void save(Role aggregate) {
        repository.saveAndFlush(aggregate);
        if (aggregate.getEventPublisher() instanceof DelayedDomainEventPublisher publisher) {
            publisher.publishDelayedEvents();
        }
    }

    @Override
    public void delete(Role aggregate) {
        if (aggregate.getEventPublisher() instanceof DelayedDomainEventPublisher publisher) {
            publisher.publishDelayedEvents();
        }
        repository.deleteByAggregateId_AggregateId(aggregate.getId());
    }
}
