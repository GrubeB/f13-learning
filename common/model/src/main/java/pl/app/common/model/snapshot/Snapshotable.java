package pl.app.common.model.snapshot;

import pl.app.common.model.Identity;

import java.io.Serializable;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * @param <ENTITY>      Type of entity
 * @param <ENTITY_ID> Type of entity identifier
 * @param <SNAPSHOT>    Type of entity snapshot
 */
public interface Snapshotable<
        ENTITY extends Identity<ENTITY_ID>,
        ENTITY_ID extends Serializable,
        SNAPSHOT extends Snapshot<ENTITY> & Identity<SnapshotId<ENTITY>>
        > {
    List<SNAPSHOT> getSnapshots();

    default SNAPSHOT getLastSnapshot() {
        if (getSnapshots() != null && !getSnapshots().isEmpty()) {
            return getSnapshots().stream()
                    .filter(version -> Objects.nonNull(version.getSnapshotNumber()))
                    .max(Comparator.comparing(Snapshot::getSnapshotNumber))
                    .orElse(null);
        } else {
            return null;
        }
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

    default Long getNextSnapshotNumber() {
        return Instant.now().toEpochMilli();
    }

    ENTITY revertSnapshot(SNAPSHOT snapshot);
}
