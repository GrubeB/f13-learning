package pl.app.common.cqrs.event.gateway;

public interface EventGateway {
    <C> void publish(C event);
}
