package pl.app.common.model.snapshot;

import pl.app.common.model.Identity;

import java.io.Serializable;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

// Snapshotable<Test, TestVersion, UUID>
public interface Snapshotable<
        S_O extends Identity<?>,                     // snapshot owner type
        S extends Snapshot<S_O> & Identity<S_ID>,    // snapshot type
        S_ID extends Serializable                    // snapshot id type
        > {
    List<S> getSnapshots();

    default S getLastSnapshot() {
        if (getSnapshots() != null && !getSnapshots().isEmpty()) {
            return getSnapshots().stream()
                    .filter(version -> Objects.nonNull(version.getSnapshotNumber()))
                    .max(Comparator.comparing(Snapshot::getSnapshotNumber))
                    .orElse(null);
        } else {
            return null;
        }
    }

    S makeSnapshot();

    default void makeAndStoreSnapshot() {
        if (getSnapshots() == null) {
            throw new RuntimeException("Collection of Snapshots must not be null, to make new snapshot");
        }
        S newSnapshot = makeSnapshot();
        newSnapshot.setSnapshotNumber(getNextSnapshotNumber());
        getSnapshots().add(newSnapshot);
    }

    default Long getNextSnapshotNumber() {
        return Instant.now().getEpochSecond();
    }

    void revertSnapshot(S snapshot);
}
