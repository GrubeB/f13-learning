package pl.app.ddd.event;

public interface DomainEventPublisherFactory {
    DomainEventPublisher getEventPublisher();
}
