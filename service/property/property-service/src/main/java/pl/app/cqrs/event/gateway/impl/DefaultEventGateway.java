package pl.app.cqrs.event.gateway.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.app.cqrs.command.gateway.impl.DefaultCommandGateway;
import pl.app.cqrs.event.bus.EventBus;
import pl.app.cqrs.event.gateway.EventGateway;

public class DefaultEventGateway implements
        EventGateway {
    private final Logger logger = LoggerFactory.getLogger(DefaultCommandGateway.class);
    private final EventBus eventBus;

    public DefaultEventGateway(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public <C> void publish(C event) {
        logger.debug("publishing event of type: " + event.getClass().getSimpleName() + "\t class: " + event.getClass().getName());
        logger.trace("event: " + event);
        this.eventBus.publish(event);
    }
}
