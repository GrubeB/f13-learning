package pl.app.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.experimental.SuperBuilder;
import pl.app.common.model.snapshot.Snapshot;
import pl.app.common.model.snapshot.SnapshotId;

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
@JsonIgnoreProperties(value = {"owner", "snapshotNumber", "createdBy", "createdDate", "lastModifiedBy", "lastModifiedDate"})
public abstract class BaseSnapshotEntity<
        ENTITY extends Identity<ENTITY_ID>,
        ENTITY_ID extends Serializable,
        SNAPSHOT extends Identity<SnapshotId<ENTITY>>
        > extends BaseAuditEntity<SNAPSHOT, SnapshotId<ENTITY>> implements
        Snapshot<ENTITY> {
    @Id
    protected SnapshotId<ENTITY> id;

    //    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    @Column(name = "snapshot_id", nullable = false, updatable = false)
//    protected SNAPSHOT_ID id;
//    @Column(name = "snapshot_number", nullable = false, updatable = false)
//    protected Long snapshotNumber;
//
//    @ManyToOne(optional = false)
//    @JoinColumn(name = "owner_id", nullable = false)
//    protected ENTITY owner;
    public BaseSnapshotEntity() {
        this.id = new SnapshotId<ENTITY>(null, Instant.now().toEpochMilli());
    }

    public BaseSnapshotEntity(ENTITY owner) {
        this.id = new SnapshotId<>(owner, Instant.now().toEpochMilli());
    }

    public BaseSnapshotEntity(ENTITY owner, Long snapshotNumber) {
        this.id = new SnapshotId<>(owner, snapshotNumber);
    }

    @Override
    public SnapshotId<ENTITY> getId() {
        return id;
    }

    @Override
    public void setId(SnapshotId<ENTITY> entityIdSnapshotId) {
        this.id = entityIdSnapshotId;
    }

    @Override
    public ENTITY getOwner() {
        return this.id.getOwner();
    }

    @Override
    public void setOwner(ENTITY owner) {
        this.id.setOwner(owner);
    }

    @Override
    public Long getSnapshotNumber() {
        return this.id.getSnapshotNumber();
    }

    @Override
    public void setSnapshotNumber(Long snapshotNumber) {
        this.id.setSnapshotNumber(snapshotNumber);
    }
}
