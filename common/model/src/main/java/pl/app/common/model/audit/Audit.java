package pl.app.common.model.audit;

import java.time.Instant;

public interface Audit {
    String getCreatedBy();

    Instant getCreatedDate();

    String getLastModifiedBy();

    Instant getLastModifiedDate();
}