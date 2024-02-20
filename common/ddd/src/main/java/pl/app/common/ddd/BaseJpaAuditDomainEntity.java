package pl.app.common.ddd;


import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import pl.app.common.model.BaseAuditEntity;
import pl.app.common.model.Identity;

import java.util.UUID;

@MappedSuperclass
public class BaseJpaAuditDomainEntity<
        ENTITY extends Identity<UUID>
        > extends BaseAuditEntity<ENTITY, UUID> implements
        DomainEntity<UUID> {
    @Id
    @Column(name = "id", nullable = false)
    protected UUID entityId;

    protected BaseJpaAuditDomainEntity() {
        this.entityId = UUID.randomUUID();
    }

    protected BaseJpaAuditDomainEntity(UUID entityId) {
        this.entityId = entityId;
    }

    @Override
    public UUID getId() {
        return entityId;
    }

    @Override
    public void setId(UUID uuid) {
        this.entityId = uuid;
    }
}
