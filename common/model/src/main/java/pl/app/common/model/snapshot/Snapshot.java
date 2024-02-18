package pl.app.common.model.snapshot;

import java.io.Serializable;

/**
 * @param <ENTITY_ID> Type of snapshot owner entity identifier
 */
public interface Snapshot<
        ENTITY_ID extends Serializable
        > {
    Long getSnapshotNumber();

    void setSnapshotNumber(Long snapshotNumber);

    ENTITY_ID getSnapshotOwnerId();

    void setOwnerId(ENTITY_ID snapshotOwnerId);
}
