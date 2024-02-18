package pl.app.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import pl.app.common.model.snapshot.Snapshot;
import pl.app.common.model.snapshot.TransientSnapshotable;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * An abstract class representing the basic class that have snapshots
 *
 * @param <ENTITY>    Type of entity
 * @param <ENTITY_ID> Type of entity identifier
 * @param <SNAPSHOT>  Type of entity snapshot
 */
@MappedSuperclass
@SuperBuilder
@JsonIgnoreProperties(value = {"snapshots", "transientSnapshots", "lastSnapshot", "nextSnapshotNumber"})
public abstract class BaseSnapshotableEntity<
        ENTITY extends Identity<ENTITY_ID>,
        ENTITY_ID extends Serializable,
        SNAPSHOT extends Snapshot<ENTITY_ID> & Identity<ENTITY_ID>
        > extends BaseAuditEntity<ENTITY, ENTITY_ID> implements
        TransientSnapshotable<ENTITY, ENTITY_ID, SNAPSHOT> {

    @OneToMany
    @JoinColumn(name = "owner_id", insertable = false, updatable = false)
    @OrderBy("snapshot_number")
    @ToString.Exclude
    private List<SNAPSHOT> snapshots;

    @Transient
    private List<SNAPSHOT> transientSnapshots;

    public BaseSnapshotableEntity() {
        this.snapshots = new LinkedList<>();
        this.transientSnapshots = new LinkedList<>();
    }

    @Override
    public List<SNAPSHOT> getSnapshots() {
        return snapshots;
    }

    @Override
    public List<SNAPSHOT> getTransientSnapshots() {
        return transientSnapshots;
    }
}
