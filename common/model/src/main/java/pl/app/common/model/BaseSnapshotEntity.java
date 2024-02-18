package pl.app.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
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
//@JsonIgnoreProperties(value = {"owner", "snapshotNumber"})
@JsonIgnoreProperties(value = {"createdBy", "createdDate", "lastModifiedBy", "lastModifiedDate"})
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

//    @ManyToOne(optional = false)
//    @JoinColumn(name = "owner_id", nullable = false)
    @Column(name = "owner_id")
    protected ENTITY_ID ownerId;

    public BaseSnapshotEntity() {
        this.snapshotNumber = Instant.now().toEpochMilli();
    }

    public BaseSnapshotEntity(ENTITY ownerId) {
        this.snapshotNumber = Instant.now().toEpochMilli();
        this.ownerId = ownerId.getId();
    }

    public BaseSnapshotEntity(ENTITY ownerId, Long snapshotNumber) {
        this.snapshotNumber = snapshotNumber;
        this.ownerId = ownerId.getId();
    }

    @Override
    public ENTITY_ID getSnapshotOwnerId() {
        return ownerId;
    }

    @Override
    public void setOwnerId(ENTITY_ID ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public ENTITY_ID getId() {
        return id;
    }

    @Override
    public void setId(ENTITY_ID id) {
        this.id = id;
    }

    @Override
    public Long getSnapshotNumber() {
        return snapshotNumber;
    }

    @Override
    public void setSnapshotNumber(Long snapshotNumber) {
        this.snapshotNumber = snapshotNumber;
    }
}
