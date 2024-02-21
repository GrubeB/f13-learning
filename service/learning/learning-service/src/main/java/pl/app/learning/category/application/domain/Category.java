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
    private Set<CategoryHasCategory> parentCategories = new LinkedHashSet<>();

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<CategoryHasCategory> childCategories = new LinkedHashSet<>();


    @SuppressWarnings("unused")
    protected Category() {
        super();
    }

    public Category(String name, String description) {
        super();
        this.name = name;
        this.description = description;
        this.status = CategoryStatus.UNVERIFIED;
    }


    public void updateInfo(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void addParentCategory(Category parentCategory) {
        if (getParentCategory(parentCategory.getAggregateId()).isPresent()) {
            return;
        }
        CategoryHasCategory categoryHasCategory = new CategoryHasCategory(parentCategory, this);
        parentCategories.add(categoryHasCategory);
    }

    public void removeParentCategory(Category parentCategory) {
        getParentCategory(parentCategory.getAggregateId())
                .ifPresent(chc -> {
                    this.parentCategories.remove(chc);
                    chc.getParent().removeChildCategory(this);
                });
    }

    public void addChildCategory(Category childCategory) {
        if (getChildCategory(childCategory.getAggregateId()).isPresent()) {
            return;
        }
        CategoryHasCategory categoryHasCategory = new CategoryHasCategory(this, childCategory);
        childCategories.add(categoryHasCategory);
    }

    public void removeChildCategory(Category childCategory) {
        getChildCategory(childCategory.getAggregateId())
                .ifPresent(chc -> {
                    this.childCategories.remove(chc);
                    chc.getChild().removeParentCategory(this);
                });
    }

    public void setStatus(CategoryStatus status) {
        this.status = status;
    }

    private Optional<CategoryHasCategory> getParentCategory(AggregateId aggregateId) {
        return this.parentCategories.stream()
                .filter(c -> Objects.equals(aggregateId, c.getParent().getAggregateId()))
                .findAny();
    }

    private Optional<CategoryHasCategory> getChildCategory(AggregateId aggregateId) {
        return this.childCategories.stream()
                .filter(c -> Objects.equals(aggregateId, c.getChild().getAggregateId()))
                .findAny();
    }

    public void setChildCategories(List<Category> categories) {
        childCategories.clear();
        categories.forEach(this::addChildCategory);
    }

    public void setParentCategories(List<Category> categories) {
        parentCategories.clear();
        categories.forEach(this::addParentCategory);
    }
}

