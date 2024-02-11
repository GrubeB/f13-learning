package pl.app.common.ddd.event;

import java.util.Collection;

public interface DelayedDomainEventPublisher extends
        DomainEventPublisher {
    /*
        publish all events, after:
        - a successful transaction
        - at the end of a business operation
        - on state changes
    */
    void publishDelayedEvents();

    @Override
    default <C> void publish(C event) {
        getEvents().add(event);
    }

    Collection<Object> getEvents();
}
