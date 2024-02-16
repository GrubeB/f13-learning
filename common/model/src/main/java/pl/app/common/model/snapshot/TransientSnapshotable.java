package pl.app.common.model.snapshot;

import pl.app.common.model.Identity;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

// TransientSnapshotable<Test, TestVersion, UUID, Short>
public interface TransientSnapshotable<
        S_O extends Identity<?>,                     // snapshot owner type
        S extends Snapshot<S_O> & Identity<S_ID>,    // snapshot type
        S_ID extends Serializable                    // snapshot id type
        > extends Snapshotable<S_O, S, S_ID> {

    List<S> getTransientSnapshots();

    default boolean hasTransientVersion() {
        return getTransientSnapshots() != null && !getTransientSnapshots().isEmpty();
    }

    @Override
    default S getLastSnapshot() {
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
    default void makeAndStoreSnapshot() {
        if (getTransientSnapshots() == null) {
            throw new RuntimeException("Collection of Snapshots must not be null, to make new snapshot");
        }
        S newSnapshot = makeSnapshot();
        newSnapshot.setSnapshotNumber(getNextSnapshotNumber());
        getTransientSnapshots().add(newSnapshot);
    }
}
