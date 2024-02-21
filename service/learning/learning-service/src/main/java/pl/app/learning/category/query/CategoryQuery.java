package pl.app.learning.category.query;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import pl.app.common.model.BaseAuditEntity;
import pl.app.learning.category.application.domain.CategoryStatus;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Immutable
@Getter
@Setter
@Table(name = "t_category")
public class CategoryQuery extends BaseAuditEntity<CategoryQuery, UUID> {
    @Id
    private UUID id;
    @Column(name = "category_name", nullable = false)
    private String name;
    @Column(length = 8000)
    private String description;
    @Enumerated(EnumType.STRING)
    private CategoryStatus status;

    @OneToMany(mappedBy = "child", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<CategoryHasCategoryQuery> parentCategories = new LinkedHashSet<>();

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<CategoryHasCategoryQuery> childCategories = new LinkedHashSet<>();
}

