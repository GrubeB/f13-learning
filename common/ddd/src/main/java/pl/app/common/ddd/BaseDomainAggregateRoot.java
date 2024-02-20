package pl.app.common.ddd;

import pl.app.common.ddd.event.DomainEventPublisher;

import java.util.UUID;

public abstract class BaseDomainAggregateRoot implements
        DomainAggregateRoot<UUID> {
    protected AggregateId aggregateId;
    protected DomainEventPublisher eventPublisher;

    public BaseDomainAggregateRoot() {
        this.aggregateId = new AggregateId();
    }

    public BaseDomainAggregateRoot(DomainEventPublisher eventPublisher) {
        this.aggregateId = new AggregateId();
        this.eventPublisher = eventPublisher;
    }

    public BaseDomainAggregateRoot(AggregateId aggregateId) {
        this.aggregateId = aggregateId;
    }

    public BaseDomainAggregateRoot(AggregateId aggregateId, DomainEventPublisher eventPublisher) {
        this.aggregateId = aggregateId;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public AggregateId getAggregateId() {
        return aggregateId;
    }

    @Override
    public void setAggregateId(AggregateId aggregateId) {
        this.aggregateId = aggregateId;
    }

    @Override
    public DomainEventPublisher getEventPublisher() {
        return this.eventPublisher;
    }

    @Override
    public void setEventPublisher(DomainEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public UUID getId() {
        return this.aggregateId.getId();
    }
}
