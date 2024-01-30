package pl.app.ddd;


import java.util.Objects;
import java.util.UUID;

public abstract class BaseEntity {
    protected UUID entityId;

    protected BaseEntity(UUID entityId) {
        this.entityId = entityId;
    }

    protected BaseEntity() {
        this.entityId = UUID.randomUUID();
    }
    public UUID getId() {
        return entityId;
    }
    @Override
    public int hashCode() {
        return entityId.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity that = (BaseEntity) o;
        return entityId != null && Objects.equals(entityId, that.entityId);
    }

    @Override
    public String toString() {
        return entityId.toString();
    }
}
