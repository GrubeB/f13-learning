package pl.app.common.model.revision;

import pl.app.common.model.Identity;
import pl.app.common.model.snapshot.Snapshot;
import pl.app.common.model.snapshot.SnapshotException;

import java.io.Serializable;
import java.time.Instant;
import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

/**
 * @param <ENTITY>    Type of entity
 * @param <ENTITY_ID> Type of entity identifier
 * @param <REVISION>  Type of revision
 * @param <REVISION_ID>  Type of revision identifier
 */
public interface Revisionable<
        ENTITY extends Identity<ENTITY_ID>,
        ENTITY_ID extends Serializable,
        REVISION extends Revision<REVISION_ID> & Identity<REVISION_ID>,
        REVISION_ID extends Serializable
        > {
    ENTITY mergeRevision(REVISION revision);
}
