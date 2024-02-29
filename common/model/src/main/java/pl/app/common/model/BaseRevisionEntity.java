package pl.app.common.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import pl.app.common.model.revision.Revision;
import pl.app.common.model.snapshot.Snapshot;

import java.io.Serializable;

/**
 * An abstract class representing the basic snapshot.
 *
 * @param <ENTITY>      Type of entity
 * @param <ENTITY_ID>   Type of entity identifier
 * @param <REVISION>    Type of revision
 * @param <REVISION_ID> Type of revision identifier
 */
@MappedSuperclass
@SuperBuilder
@Getter
@Setter
public abstract class BaseRevisionEntity<
        ENTITY extends Identity<ENTITY_ID>,
        ENTITY_ID extends Serializable,
        REVISION extends Revision<REVISION_ID> & Identity<REVISION_ID>,
        REVISION_ID extends Serializable
        > extends BaseAuditEntity<REVISION, REVISION_ID> implements
        Revision<ENTITY_ID> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "snapshot_id", nullable = false, updatable = false)
    protected REVISION_ID id;

    @Column(name = "owner_id")
    protected ENTITY_ID revisionOwnerId;

    public BaseRevisionEntity() {
    }

    public BaseRevisionEntity(ENTITY revisionOwner) {
        this.revisionOwnerId = revisionOwner.getId();
    }
}
