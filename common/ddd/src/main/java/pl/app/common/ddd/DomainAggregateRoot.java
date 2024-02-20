package pl.app.common.ddd;

import pl.app.common.ddd.event.DomainEventPublisher;

public interface DomainAggregateRoot<ID> {
    AggregateId getAggregateId();
    void setAggregateId(AggregateId aggregateId);
    DomainEventPublisher getEventPublisher();
    void setEventPublisher(DomainEventPublisher eventPublisher);
    ID getId();
}
