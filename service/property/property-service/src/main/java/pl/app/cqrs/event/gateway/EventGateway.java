package pl.app.cqrs.event.gateway;

public interface EventGateway {
    <C> void publish(C event);
}
