package pl.app.common.model.snapshot;

import pl.app.common.model.Identity;

/**
 * @param <ENTITY> Type of snapshot owner entity
 */
public interface Snapshot<
        ENTITY extends Identity<?>
        > {
    Long getSnapshotNumber();

    void setSnapshotNumber(Long snapshotNumber);

    ENTITY getOwner();

    void setOwner(ENTITY snapshotOwner);
}
