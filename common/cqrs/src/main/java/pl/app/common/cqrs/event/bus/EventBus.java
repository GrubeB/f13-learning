package pl.app.common.cqrs.event.bus;

public interface EventBus {
    <C> void publish(C event);
}
