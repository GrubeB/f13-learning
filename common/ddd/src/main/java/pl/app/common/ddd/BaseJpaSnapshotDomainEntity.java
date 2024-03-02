package pl.app.common.ddd;


import jakarta.persistence.MappedSuperclass;
import lombok.experimental.SuperBuilder;
import pl.app.common.model.BaseSnapshotEntity;
import pl.app.common.model.Identity;
import pl.app.common.model.snapshot.Snapshot;

import java.time.Instant;
import java.util.UUID;

@MappedSuperclass
@SuperBuilder
public class BaseJpaSnapshotDomainEntity<
        ENTITY extends Identity<UUID>,
        SNAPSHOT extends Snapshot<UUID> & Identity<UUID>
        > extends BaseSnapshotEntity<ENTITY, UUID, SNAPSHOT> implements
        DomainEntity<UUID> {
    public BaseJpaSnapshotDomainEntity() {
        this.snapshotNumber = Instant.now().toEpochMilli();
    }

    public BaseJpaSnapshotDomainEntity(ENTITY snapshotOwner) {
        this.snapshotNumber = Instant.now().toEpochMilli();
        this.snapshotOwnerId = snapshotOwner.getId();
    }
    public BaseJpaSnapshotDomainEntity(UUID snapshotOwnerId) {
        this.snapshotNumber = Instant.now().toEpochMilli();
        this.snapshotOwnerId = snapshotOwnerId;
    }
    public BaseJpaSnapshotDomainEntity(ENTITY snapshotOwner, Long snapshotNumber) {
        this.snapshotNumber = snapshotNumber;
        this.snapshotOwnerId = snapshotOwner.getId();
    }
}
