package pl.app.authorization.permision.application.domain;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.app.common.ddd.annotation.FactoryAnnotation;
import pl.app.common.ddd.event.DomainEventPublisherFactory;

@FactoryAnnotation
@Component
@RequiredArgsConstructor
public class PermissionFactory {
    private final DomainEventPublisherFactory domainEventPublisherFactory;

    public Permission create(String name) {
        return new Permission(name, domainEventPublisherFactory.getEventPublisher());
    }
}
