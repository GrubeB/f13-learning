package pl.app.authorization.user.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.authorization.user.application.domain.User;
import pl.app.authorization.user.application.domain.UserException;
import pl.app.authorization.user.application.port.out.UserDomainRepositoryPort;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.event.DelayedDomainEventPublisher;
import pl.app.common.ddd.event.DomainEventPublisherFactory;

@Component
@Transactional
@RequiredArgsConstructor
class UserPersistenceAdapter implements
        UserDomainRepositoryPort {
    private final UserRepository repository;
    private final DomainEventPublisherFactory domainEventPublisherFactory;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User load(AggregateId aggregateId) {
        var aggregate = repository.findById(aggregateId)
                .orElseThrow(() -> UserException.NotFoundUserException.fromId(aggregateId.getId()));
        aggregate.setEventPublisher(domainEventPublisherFactory.getEventPublisher());
        aggregate.setPasswordEncoder(passwordEncoder);
        return aggregate;
    }

    @Override
    public void save(User aggregate) {
        repository.saveAndFlush(aggregate);
        if (aggregate.getEventPublisher() instanceof DelayedDomainEventPublisher publisher) {
            publisher.publishDelayedEvents();
        }
    }
}
