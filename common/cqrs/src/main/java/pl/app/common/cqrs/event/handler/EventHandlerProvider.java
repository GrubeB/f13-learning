package pl.app.common.cqrs.event.handler;

import java.util.Set;

public interface EventHandlerProvider {
    <E> Set<EventHandler<E>> getHandlers(E event);
}
