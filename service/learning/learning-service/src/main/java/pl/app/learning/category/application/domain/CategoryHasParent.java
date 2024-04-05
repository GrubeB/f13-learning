package pl.app.learning.category.application.domain;


import jakarta.persistence.*;
import lombok.Getter;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.BaseJpaAuditDomainEntity;
import pl.app.common.ddd.annotation.EntityAnnotation;

@EntityAnnotation
@Entity
@Getter
@Table(name = "t_category_has_category")
public class CategoryHasParent extends BaseJpaAuditDomainEntity<CategoryHasParent> {

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_1_id", nullable = false, updatable = false)
    private Category child;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "aggregateId", column = @Column(name = "category_2_id", nullable = false, updatable = false))
    })
    private AggregateId parent;

    @SuppressWarnings("unused")
    protected CategoryHasParent() {
        super();
    }

    public CategoryHasParent(AggregateId parent, Category child) {
        this.child = child;
        this.parent = parent;
    }
}

