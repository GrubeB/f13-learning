package pl.app.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import pl.app.common.audit.Audit;
import pl.app.common.model.snapshot.Snapshot;

import java.io.Serializable;

@MappedSuperclass
@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(value = {"owner"})
public abstract class BaseSnapshotEntity<
        S_O extends Identity<?>,        // snapshot owner type
        ID extends Serializable         // snapshot ID type
        > extends Audit implements
        Snapshot<S_O>,
        Identity<ID> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "snapshot_id", nullable = false, updatable = false)
    protected ID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    private S_O owner;

    @Column(name = "snapshot_number", nullable = false, updatable = false)
    protected Long snapshotNumber;
}
