package pl.app.common.ddd.event;

public interface DomainEventPublisherFactory {
    DomainEventPublisher getEventPublisher();
}
