package pl.app.common.ddd;

import jakarta.persistence.*;
import pl.app.common.ddd.event.DomainEventPublisher;
import pl.app.common.model.BaseAuditEntity;
import pl.app.common.model.BaseSnapshotableEntity;
import pl.app.common.model.Identity;
import pl.app.common.model.snapshot.Snapshot;

import java.util.UUID;

@MappedSuperclass
public abstract class BaseJpaSnapshotableDomainAggregateRoot<
        ENTITY extends Identity<UUID>,
        SNAPSHOT extends Snapshot<UUID> & Identity<UUID>
        > extends BaseSnapshotableEntity<ENTITY, UUID, SNAPSHOT> implements
        DomainAggregateRoot<UUID> {

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "aggregateId", column = @Column(name = "id", nullable = false))
    })
    protected AggregateId aggregateId;

    @Transient
    protected DomainEventPublisher eventPublisher;

    public BaseJpaSnapshotableDomainAggregateRoot() {
        this.aggregateId = new AggregateId();
    }

    public BaseJpaSnapshotableDomainAggregateRoot(DomainEventPublisher eventPublisher) {
        this.aggregateId = new AggregateId();
        this.eventPublisher = eventPublisher;
    }

    public BaseJpaSnapshotableDomainAggregateRoot(AggregateId aggregateId) {
        this.aggregateId = aggregateId;
    }

    public BaseJpaSnapshotableDomainAggregateRoot(AggregateId aggregateId, DomainEventPublisher eventPublisher) {
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

    @Override
    public void setId(UUID uuid) {
        this.aggregateId = new AggregateId(uuid);
    }
}
