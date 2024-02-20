package pl.app.common.ddd;


import java.util.UUID;

public abstract class BaseDomainEntity implements
        DomainEntity<UUID> {
    protected UUID entityId;

    protected BaseDomainEntity() {
        this.entityId = UUID.randomUUID();
    }

    protected BaseDomainEntity(UUID entityId) {
        this.entityId = entityId;
    }

    @Override
    public UUID getId() {
        return entityId;
    }
}
