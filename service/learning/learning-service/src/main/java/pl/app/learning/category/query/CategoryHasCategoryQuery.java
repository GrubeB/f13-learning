package pl.app.learning.category.query;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import pl.app.common.model.BaseAuditEntity;
import pl.app.learning.category.query.model.CategoryQuery;

import java.util.UUID;

@Entity
@Immutable
@Getter
@Setter
@Table(name = "t_category_has_category")
public class CategoryHasCategoryQuery extends BaseAuditEntity<CategoryHasCategoryQuery, UUID> {
    @Id
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_1_id", nullable = false, updatable = false)
    private CategoryQuery child;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_2_id", nullable = false, updatable = false)
    private CategoryQuery parent;
}

