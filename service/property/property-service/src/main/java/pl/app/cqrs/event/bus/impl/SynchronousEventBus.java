package pl.app.cqrs.event.bus.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.app.cqrs.event.bus.EventBus;
import pl.app.cqrs.event.handler.EventHandler;
import pl.app.cqrs.event.handler.EventHandlerProvider;

import java.util.Set;

public class SynchronousEventBus implements
        EventBus {
    private final Logger logger = LoggerFactory.getLogger(SynchronousEventBus.class);
    private final EventHandlerProvider eventHandlerProvider;

    public SynchronousEventBus(EventHandlerProvider eventHandlerProvider) {
        this.eventHandlerProvider = eventHandlerProvider;
    }

    @Override
    public <C> void publish(C event) {
        Set<EventHandler<C>> handlers = this.eventHandlerProvider.getHandlers(event);
        handlers.forEach(handler -> {
            try {
                handler.handle(event);
            } catch (Exception e) {
                logger.warn("event handler could not handle event of type: " + event.getClass() + ". Error: " + e);
            }
        });
    }
}
