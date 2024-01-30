package pl.app.cqrs.event.bus;

public interface EventBus {
    <C> void publish(C event);
}
