package pl.app.cqrs.event.handler;


import java.util.Set;

/*
 * Event handlers container
 * */
public interface EventHandlerContext {
    void registerEventHandling(EventHandler<?> eventHandler);

    void registerEventHandler(Object eventListener);

    Set<EventHandler<?>> getHandlers();
}
