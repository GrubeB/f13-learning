package pl.app.cqrs.event.gateway.impl;

import pl.app.cqrs.event.bus.EventBus;
import pl.app.cqrs.event.gateway.EventGateway;

public class DefaultEventGateway implements
        EventGateway {
    private final EventBus eventBus;

    public DefaultEventGateway(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public <C> void publish(C event) {
        this.eventBus.publish(event);
    }
}
