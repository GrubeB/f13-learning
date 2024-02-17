package pl.app.common.ddd;

import jakarta.persistence.*;
import pl.app.common.ddd.event.DomainEventPublisher;
import pl.app.common.model.BaseAuditEntity;

import java.util.UUID;

@MappedSuperclass
public abstract class JpaBaseAggregateRoot extends BaseAuditEntity<JpaBaseAggregateRoot, UUID> {

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "aggregateId", column = @Column(name = "id", nullable = false))
    })
    protected AggregateId aggregateId;

    @Transient
    protected DomainEventPublisher eventPublisher;

    public JpaBaseAggregateRoot() {
        this.aggregateId = new AggregateId();
    }

    public JpaBaseAggregateRoot(DomainEventPublisher eventPublisher) {
        this.aggregateId = new AggregateId();
        this.eventPublisher = eventPublisher;
    }

    public JpaBaseAggregateRoot(AggregateId aggregateId) {
        this.aggregateId = aggregateId;
    }

    public JpaBaseAggregateRoot(AggregateId aggregateId, DomainEventPublisher eventPublisher) {
        this.aggregateId = aggregateId;
        this.eventPublisher = eventPublisher;
    }

    public AggregateId getAggregateId() {
        return aggregateId;
    }

    @Override
    public UUID getId() {
        return aggregateId.getId();
    }

    @Override
    public void setId(UUID uuid) {
        this.aggregateId = new AggregateId(uuid);
    }

    public DomainEventPublisher getEventPublisher() {
        return eventPublisher;
    }

    public void setEventPublisher(DomainEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }
}
