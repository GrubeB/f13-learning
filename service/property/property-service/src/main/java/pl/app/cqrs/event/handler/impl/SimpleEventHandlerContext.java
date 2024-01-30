package pl.app.cqrs.event.handler.impl;


import pl.app.cqrs.event.handler.EventHandler;
import pl.app.cqrs.event.handler.EventHandlerContext;
import pl.app.cqrs.event.annotation.EventHandlingAnnotation;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

public class SimpleEventHandlerContext implements
        EventHandlerContext {
    private final Set<EventHandler<?>> eventHandlers = new HashSet<>();

    @Override
    public Set<EventHandler<?>> getHandlers() {
        return eventHandlers;
    }

    @Override
    public void registerEventHandling(EventHandler<?> eventHandler) {
        this.eventHandlers.add(eventHandler);
    }

    @Override
    public void registerEventHandler(Object eventHandler) {
        final Method[] methods = eventHandler.getClass().getMethods();
        Stream.of(methods).forEach(method -> {
            EventHandlingAnnotation annotation = method.getAnnotation(EventHandlingAnnotation.class);
            if (Objects.nonNull(annotation)) {
                final Class<?>[] methodParameterTypes = method.getParameterTypes();
                this.registerEventHandling(new SimpleEventHandler<>(eventHandler, method, methodParameterTypes[0]));
            }
        });
    }
}
