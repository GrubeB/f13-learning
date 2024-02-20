package pl.app.common.ddd;


import jakarta.persistence.MappedSuperclass;
import pl.app.common.model.BaseSnapshotEntity;
import pl.app.common.model.Identity;
import pl.app.common.model.snapshot.Snapshot;

import java.time.Instant;
import java.util.UUID;

@MappedSuperclass
public class BaseJpaSnapshotDomainEntity<
        ENTITY extends Identity<UUID>,
        SNAPSHOT extends Snapshot<UUID> & Identity<UUID>
        > extends BaseSnapshotEntity<ENTITY, UUID, SNAPSHOT> implements
        DomainEntity<UUID> {
    public BaseJpaSnapshotDomainEntity() {
        this.snapshotNumber = Instant.now().toEpochMilli();
    }

    public BaseJpaSnapshotDomainEntity(ENTITY snapshotOwnerId) {
        this.snapshotNumber = Instant.now().toEpochMilli();
        this.snapshotOwnerId = snapshotOwnerId.getId();
    }

    public BaseJpaSnapshotDomainEntity(ENTITY snapshotOwnerId, Long snapshotNumber) {
        this.snapshotNumber = snapshotNumber;
        this.snapshotOwnerId = snapshotOwnerId.getId();
    }
}
