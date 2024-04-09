package pl.app.learning.category.application.domain;


import jakarta.persistence.*;
import lombok.Getter;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.BaseJpaAuditDomainAggregateRoot;
import pl.app.common.ddd.annotation.AggregateRootAnnotation;

import java.util.*;

@AggregateRootAnnotation
@Entity
@Getter
@Table(name = "t_category")
public class Category extends BaseJpaAuditDomainAggregateRoot<Category> {
    @Column(name = "category_name", nullable = false)
    private String name;
    @Column(length = 8000)
    private String description;
    @Enumerated(EnumType.STRING)
    private CategoryStatus status;

    @OneToMany(mappedBy = "child", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private final Set<CategoryHasParent> parentCategories = new LinkedHashSet<>();

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private final Set<CategoryHasChild> childCategories = new LinkedHashSet<>();

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "aggregateId", column = @Column(name = "voting_id"))
    })
    private AggregateId voting;

    @SuppressWarnings("unused")
    protected Category() {
        super();
    }

    public Category(String name, String description, List<AggregateId> parentCategories, List<AggregateId> childCategories) {
        super();
        this.name = name;
        this.description = description;
        this.status = CategoryStatus.UNVERIFIED;
        setParentCategories(parentCategories);
        setChildCategories(childCategories);
    }


    public void updateInfo(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void addParentCategory(AggregateId parentCategory) {
        if (getParentCategory(parentCategory).isPresent()) {
            return;
        }
        CategoryHasParent categoryHasCategory = new CategoryHasParent(parentCategory, this);
        parentCategories.add(categoryHasCategory);
    }

    public void removeParentCategory(AggregateId parentCategory) {
        getParentCategory(parentCategory)
                .ifPresent(this.parentCategories::remove);
    }

    public void addChildCategory(AggregateId childCategory) {
        if (getChildCategory(childCategory).isPresent()) {
            return;
        }
        CategoryHasChild categoryHasCategory = new CategoryHasChild(this, childCategory);
        childCategories.add(categoryHasCategory);
    }

    public void removeChildCategory(AggregateId childCategory) {
        getChildCategory(childCategory)
                .ifPresent(this.childCategories::remove);
    }

    public void setStatus(CategoryStatus status) {
        this.status = status;
    }

    private Optional<CategoryHasParent> getParentCategory(AggregateId aggregateId) {
        return this.parentCategories.stream()
                .filter(c -> Objects.equals(aggregateId, c.getParent()))
                .findAny();
    }

    private Optional<CategoryHasChild> getChildCategory(AggregateId aggregateId) {
        return this.childCategories.stream()
                .filter(c -> Objects.equals(aggregateId, c.getChild()))
                .findAny();
    }

    public void setParentCategories(List<AggregateId> parentCategories) {
        this.parentCategories.clear();
        parentCategories.forEach(this::addParentCategory);
    }

    public void setChildCategories(List<AggregateId> childCategories) {
        this.childCategories.clear();
        childCategories.forEach(this::addChildCategory);
    }

    public void setVoting(AggregateId voting) {
        this.voting = voting;
    }
}

