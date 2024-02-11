package pl.app.common.cqrs.event.bus.impl;

import pl.app.common.cqrs.event.bus.EventBus;
import pl.app.common.cqrs.event.handler.EventHandler;
import pl.app.common.cqrs.event.handler.EventHandlerProvider;

import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AsynchronousEventBus implements
        EventBus {
    private final Executor executor = Executors.newFixedThreadPool(10);
    private final EventHandlerProvider eventHandlerProvider;

    public AsynchronousEventBus(EventHandlerProvider eventHandlerProvider) {
        this.eventHandlerProvider = eventHandlerProvider;
    }

    @Override
    public <C> void publish(C event) {
        Set<EventHandler<C>> handlers = this.eventHandlerProvider.getHandlers(event);
        handlers.forEach(handler -> executor.execute(() -> handler.handle(event)));
    }
}
