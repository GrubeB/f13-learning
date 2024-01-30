package pl.app.ddd.event;

public interface DomainEventPublisher {
    <C> void publish(C event);
}
