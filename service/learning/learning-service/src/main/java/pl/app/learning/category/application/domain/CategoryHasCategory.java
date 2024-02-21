package pl.app.learning.category.application.domain;


import jakarta.persistence.*;
import lombok.Getter;
import pl.app.common.ddd.BaseJpaAuditDomainEntity;
import pl.app.common.ddd.annotation.EntityAnnotation;

@EntityAnnotation
@Entity
@Getter
@Table(name = "t_category_has_category")
public class CategoryHasCategory extends BaseJpaAuditDomainEntity<CategoryHasCategory> {

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_1_id", nullable = false, updatable = false)
    private Category child;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_2_id", nullable = false, updatable = false)
    private Category parent;

    @SuppressWarnings("unused")
    protected CategoryHasCategory() {
        super();
    }
    public CategoryHasCategory(Category parent, Category child) {
        this.child = child;
        this.parent = parent;
    }
}

