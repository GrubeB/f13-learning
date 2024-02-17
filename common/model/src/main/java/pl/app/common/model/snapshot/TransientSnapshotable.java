package pl.app.common.model.snapshot;

import pl.app.common.model.Identity;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * @param <ENTITY>      Type of entity
 * @param <ENTITY_ID> Type of entity identifier
 * @param <SNAPSHOT>    Type of entity snapshot
 */
public interface TransientSnapshotable<
        ENTITY extends Identity<ENTITY_ID>,
        ENTITY_ID extends Serializable,
        SNAPSHOT extends Snapshot<ENTITY> & Identity<SnapshotId<ENTITY>>
        > extends Snapshotable<ENTITY, ENTITY_ID, SNAPSHOT> {

    List<SNAPSHOT> getTransientSnapshots();

    default boolean hasTransientVersion() {
        return getTransientSnapshots() != null && !getTransientSnapshots().isEmpty();
    }

    @Override
    default SNAPSHOT getLastSnapshot() {
        if (hasTransientVersion()) {
            return getTransientSnapshots().stream()
                    .filter(version -> Objects.nonNull(version.getSnapshotNumber()))
                    .max(Comparator.comparing(Snapshot::getSnapshotNumber))
                    .orElse(null);
        } else {
            return Snapshotable.super.getLastSnapshot();
        }
    }

    @Override
    default SNAPSHOT makeAndStoreSnapshot() {
        if (getTransientSnapshots() == null) {
            throw new SnapshotException.NullCollectionException();
        }
        SNAPSHOT newSnapshot = makeSnapshot();
        newSnapshot.setSnapshotNumber(getNextSnapshotNumber());
        getTransientSnapshots().add(newSnapshot);
        return newSnapshot;
    }
}
