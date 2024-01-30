package pl.app.ddd;

import pl.app.ddd.event.DomainEventPublisher;

import java.util.UUID;

public abstract class BaseAggregateRoot {
    protected AggregateId aggregateId;
    protected DomainEventPublisher eventPublisher;

    public BaseAggregateRoot() {
        this.aggregateId = new AggregateId();
    }

    public BaseAggregateRoot(DomainEventPublisher eventPublisher) {
        this.aggregateId = new AggregateId();
        this.eventPublisher = eventPublisher;
    }
    public BaseAggregateRoot(AggregateId aggregateId) {
        this.aggregateId = aggregateId;
    }
    public BaseAggregateRoot(AggregateId aggregateId, DomainEventPublisher eventPublisher) {
        this.aggregateId = aggregateId;
        this.eventPublisher = eventPublisher;
    }

    public AggregateId getAggregateId() {
        return aggregateId;
    }

    public UUID getId() {
        return aggregateId.getId();
    }

    public void setEventPublisher(DomainEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }
}
