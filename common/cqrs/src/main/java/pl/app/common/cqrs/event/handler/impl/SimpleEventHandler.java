package pl.app.common.cqrs.event.handler.impl;

import pl.app.common.cqrs.event.handler.EventHandler;

import java.lang.reflect.Method;

public class SimpleEventHandler<E> implements
        EventHandler<E> {
    private final Object object;
    private final Method method;
    private final Class<E> eventType;

    public SimpleEventHandler(Object object, Method method, Class<E> eventType) {
        this.object = object;
        this.method = method;
        this.eventType = eventType;
    }

    @Override
    public boolean canHandle(Object event) {
        return eventType.isAssignableFrom(event.getClass());
    }

    @Override
    public void handle(E event) {
        try {
            method.invoke(object, event);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
