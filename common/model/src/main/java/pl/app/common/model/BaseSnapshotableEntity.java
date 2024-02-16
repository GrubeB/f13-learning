package pl.app.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import pl.app.common.audit.Audit;
import pl.app.common.model.snapshot.Snapshot;
import pl.app.common.model.snapshot.TransientSnapshotable;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@MappedSuperclass
@SuperBuilder
@NoArgsConstructor
@JsonIgnoreProperties(value = {"snapshots", "transientSnapshots", "lastSnapshot","nextSnapshotNumber"})
public abstract class BaseSnapshotableEntity<
        S_O extends Identity<?>,                        // snapshot owner type
        ID extends Serializable,                        // snapshot owner id type
        V extends Snapshot<S_O> & Identity<ID>          // snapshot type
        > extends Audit implements
        TransientSnapshotable<S_O, V, ID>,
        Identity<ID> {

    @OneToMany
    @JoinColumn(name = "owner_id")
    @OrderBy("snapshot_number")
    @Builder.Default
    @ToString.Exclude
    private List<V> snapshots = new LinkedList<>();

    @Transient
    @Builder.Default
    private final List<V> transientSnapshots = new LinkedList<>();

    @Override
    public List<V> getSnapshots() {
        return snapshots;
    }

    @Override
    public List<V> getTransientSnapshots() {
        return transientSnapshots;
    }
}
