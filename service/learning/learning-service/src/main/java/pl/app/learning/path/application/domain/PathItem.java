package pl.app.learning.path.application.domain;

import jakarta.persistence.*;
import lombok.Getter;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.BaseJpaAuditDomainEntity;
import pl.app.common.ddd.annotation.EntityAnnotation;

@EntityAnnotation
@Entity
@Getter
@Table(name = "t_path_item")
public class PathItem extends BaseJpaAuditDomainEntity<PathItem> {
    @Column(name = "item_number")
    private Long number;
    @Enumerated(EnumType.STRING)
    @Column(name = "item_type")
    private ItemType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "entity_type")
    private ItemEntityType entityType;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "aggregateId", column = @Column(name = "entity_id", nullable = false, updatable = false))
    })
    private AggregateId entity;


    @ManyToOne(optional = false)
    @JoinColumn(name = "path_id", nullable = false, updatable = false)
    private Path path;

    @SuppressWarnings("unused")
    protected PathItem() {
        super();
    }

    public PathItem(Long number, ItemType type, ItemEntityType entityType, AggregateId entity) {
        this.number = number;
        this.type = type;
        this.entityType = entityType;
        this.entity = entity;
    }

    public void setPath(Path path) {
        this.path = path;
    }
}
