package pl.app.common.model.snapshot;

import pl.app.common.model.Identity;

import java.io.Serializable;
import java.time.Instant;
import java.util.*;

/**
 * @param <ENTITY>    Type of entity
 * @param <ENTITY_ID> Type of entity identifier
 * @param <SNAPSHOT>  Type of snapshot
 */
public interface Snapshotable<
        ENTITY extends Identity<ENTITY_ID>,
        ENTITY_ID extends Serializable,
        SNAPSHOT extends Snapshot<ENTITY_ID> & Identity<ENTITY_ID>
        > {
    Collection<SNAPSHOT> getSnapshots();

    default Optional<SNAPSHOT> getLastSnapshot() {
        if (getSnapshots() != null && !getSnapshots().isEmpty()) {
            return getSnapshots().stream()
                    .filter(snapshot -> Objects.nonNull(snapshot.getSnapshotNumber()))
                    .max(Comparator.comparing(Snapshot::getSnapshotNumber));
        } else {
            return Optional.empty();
        }
    }

    default Optional<SNAPSHOT> getSnapshotBySnapshotNumber(Long snapshotNumber) {
        if (getSnapshots() != null && !getSnapshots().isEmpty()) {
            return getSnapshots().stream()
                    .filter(snapshot -> Objects.equals(snapshot.getSnapshotNumber(), snapshotNumber))
                    .findFirst();
        } else {
            return Optional.empty();
        }
    }
    default Long getNextSnapshotNumber() {
        return Instant.now().toEpochMilli();
    }

    SNAPSHOT makeSnapshot();

    default SNAPSHOT makeAndStoreSnapshot() {
        if (getSnapshots() == null) {
            throw new SnapshotException.NullCollectionException();
        }
        SNAPSHOT newSnapshot = makeSnapshot();
        newSnapshot.setSnapshotNumber(getNextSnapshotNumber());
        getSnapshots().add(newSnapshot);
        return newSnapshot;
    }


    ENTITY revertSnapshot(SNAPSHOT snapshot);
}
