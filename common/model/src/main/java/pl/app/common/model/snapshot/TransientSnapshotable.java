package pl.app.common.model.snapshot;

import pl.app.common.model.Identity;

import java.io.Serializable;
import java.util.*;

/**
 * @param <ENTITY>    Type of entity
 * @param <ENTITY_ID> Type of entity identifier
 * @param <SNAPSHOT>  Type of snapshot
 */
public interface TransientSnapshotable<
        ENTITY extends Identity<ENTITY_ID>,
        ENTITY_ID extends Serializable,
        SNAPSHOT extends Snapshot<ENTITY_ID> & Identity<ENTITY_ID>
        > extends Snapshotable<ENTITY, ENTITY_ID, SNAPSHOT> {

    Collection<SNAPSHOT> getTransientSnapshots();

    default boolean hasTransientVersion() {
        return getTransientSnapshots() != null && !getTransientSnapshots().isEmpty();
    }

    @Override
    default Optional<SNAPSHOT> getLastSnapshot() {
        if (hasTransientVersion()) {
            return getTransientSnapshots().stream()
                    .filter(version -> Objects.nonNull(version.getSnapshotNumber()))
                    .max(Comparator.comparing(Snapshot::getSnapshotNumber));
        } else {
            return Snapshotable.super.getLastSnapshot();
        }
    }

    @Override
    default Optional<SNAPSHOT> getSnapshotBySnapshotNumber(Long snapshotNumber) {
        if (hasTransientVersion()) {
            return getTransientSnapshots().stream()
                    .filter(snapshot -> Objects.equals(snapshot.getSnapshotNumber(), snapshotNumber))
                    .findFirst();
        } else {
            return Snapshotable.super.getSnapshotBySnapshotNumber(snapshotNumber);
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
