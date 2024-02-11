package pl.app.common.ddd;

import java.util.Objects;
import java.util.UUID;

public class AggregateId {
    private UUID aggregateId;

    public AggregateId(UUID aggregateId) {
        this.aggregateId = aggregateId;
    }

    public AggregateId() {
        this.aggregateId = UUID.randomUUID();
    }

    public UUID getId() {
        return aggregateId;
    }

    @Override
    public int hashCode() {
        return aggregateId.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AggregateId that = (AggregateId) o;
        return aggregateId != null && Objects.equals(aggregateId, that.aggregateId);
    }

    @Override
    public String toString() {
        return aggregateId.toString();
    }
}
