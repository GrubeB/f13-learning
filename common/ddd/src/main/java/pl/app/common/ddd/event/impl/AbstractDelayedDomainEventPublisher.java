package pl.app.common.ddd.event.impl;

import pl.app.common.ddd.event.DelayedDomainEventPublisher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class AbstractDelayedDomainEventPublisher implements
        DelayedDomainEventPublisher {
    private final List<Object> events;

    public AbstractDelayedDomainEventPublisher() {
        this.events = new ArrayList<>();
    }

    @Override
    public Collection<Object> getEvents() {
        return events;
    }
}
