package pl.app.common.model.revision;

import java.io.Serializable;

/**
 * @param <ENTITY_ID> Type of revision owner entity identifier
 */
public interface Revision<
        ENTITY_ID extends Serializable
        > {
    ENTITY_ID getRevisionOwnerId();

    void setRevisionOwnerId(ENTITY_ID revisionOwnerId);
}
