package pl.app.cqrs.event.handler.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.app.cqrs.event.handler.EventHandler;
import pl.app.cqrs.event.handler.EventHandlerContext;
import pl.app.cqrs.event.handler.EventHandlerProvider;

import java.util.Set;
import java.util.stream.Collectors;


public class SimpleEventHandlerProvider implements
        EventHandlerProvider {
    private final Logger logger = LoggerFactory.getLogger(SimpleEventHandlerProvider.class);
    private final EventHandlerContext eventHandlerContext;

    public SimpleEventHandlerProvider(EventHandlerContext eventHandlerContext) {
        this.eventHandlerContext = eventHandlerContext;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E> Set<EventHandler<E>> getHandlers(E event) {
        Set<EventHandler<E>> handlers = eventHandlerContext.getHandlers().stream()
                .filter(eventHandler -> eventHandler.canHandle(event))
                .map(handler -> (EventHandler<E>) handler)
                .collect(Collectors.toSet());
        if (handlers.isEmpty()) {
            logger.warn("not found event handler that can handle event of type: " + event.getClass());
        }
        return handlers;
    }
}
