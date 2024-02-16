package pl.app.common.model.snapshot;

import pl.app.common.model.Identity;

// Version<Test>
public interface Snapshot<
        S_O extends Identity<?>    // snapshot owner type
        > {
    Long getSnapshotNumber();

    void setSnapshotNumber(Long snapshotNumber);

    S_O getOwner();

    void setOwner(S_O snapshotOwner);
}
