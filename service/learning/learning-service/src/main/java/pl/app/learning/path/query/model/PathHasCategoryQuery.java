package pl.app.learning.path.query.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import pl.app.common.model.BaseAuditEntity;
import pl.app.learning.category.query.CategoryQuery;

import java.util.UUID;

@Immutable
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_path_has_category")
public class PathHasCategoryQuery extends BaseAuditEntity<PathHasCategoryQuery, UUID> {
    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryQuery category;

    @ManyToOne(optional = false)
    @JoinColumn(name = "path_id", nullable = false, updatable = false)
    private PathQuery path;
}

