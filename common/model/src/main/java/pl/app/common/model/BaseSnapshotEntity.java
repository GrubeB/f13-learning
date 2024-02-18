package pl.app.common.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import pl.app.common.model.snapshot.Snapshot;

import java.io.Serializable;
import java.time.Instant;

/**
 * An abstract class representing the basic snapshot.
 *
 * @param <ENTITY>    Type of entity
 * @param <ENTITY_ID> Type of entity identifier
 * @param <SNAPSHOT>  Type of entity snapshot
 */
@MappedSuperclass
@SuperBuilder
@Getter
@Setter
public abstract class BaseSnapshotEntity<
        ENTITY extends Identity<ENTITY_ID>,
        ENTITY_ID extends Serializable,
        SNAPSHOT extends Identity<ENTITY_ID>
        > extends BaseAuditEntity<SNAPSHOT, ENTITY_ID> implements
        Snapshot<ENTITY_ID> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "snapshot_id", nullable = false, updatable = false)
    protected ENTITY_ID id;

    @Column(name = "snapshot_number", nullable = false, updatable = false)
    protected Long snapshotNumber;

    @Column(name = "owner_id")
    protected ENTITY_ID snapshotOwnerId;

    public BaseSnapshotEntity() {
        this.snapshotNumber = Instant.now().toEpochMilli();
    }

    public BaseSnapshotEntity(ENTITY snapshotOwnerId) {
        this.snapshotNumber = Instant.now().toEpochMilli();
        this.snapshotOwnerId = snapshotOwnerId.getId();
    }

    public BaseSnapshotEntity(ENTITY snapshotOwnerId, Long snapshotNumber) {
        this.snapshotNumber = snapshotNumber;
        this.snapshotOwnerId = snapshotOwnerId.getId();
    }
}
