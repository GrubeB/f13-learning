package pl.app.common.ddd.event;

public interface DomainEventPublisher {
    <C> void publish(C event);
}
