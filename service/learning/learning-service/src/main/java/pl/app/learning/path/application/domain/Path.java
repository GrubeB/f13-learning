package pl.app.learning.path.application.domain;


import jakarta.persistence.*;
import lombok.Getter;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.BaseJpaAuditDomainAggregateRoot;
import pl.app.common.ddd.annotation.AggregateRootAnnotation;
import pl.app.common.mapper.Join;
import pl.app.common.mapper.MergerUtils;

import java.util.*;
import java.util.stream.Collectors;

@AggregateRootAnnotation
@Entity
@Getter
@Table(name = "t_path")
public class Path extends BaseJpaAuditDomainAggregateRoot<Path> {
    @Column(name = "path_name")
    private String name;
    @Column(name = "path_content")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "path_status")
    private PathStatus status;

    @OneToMany(mappedBy = "path", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private final Set<PathHasCategory> categories = new LinkedHashSet<>();

    @OneToMany(mappedBy = "path", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private final Set<PathItem> items = new LinkedHashSet<>();

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "aggregateId", column = @Column(name = "voting_id"))
    })
    private AggregateId voting;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "aggregateId", column = @Column(name = "comment_container_id"))
    })
    private AggregateId commentContainer;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "aggregateId", column = @Column(name = "progress_container_id"))
    })
    private AggregateId progressContainer;

    @SuppressWarnings("unused")
    protected Path() {
        super();
    }

    public Path(String name, String content, PathStatus status, List<AggregateId> categories, List<PathItem> items) {
        this.name = name;
        this.content = content;
        this.status = status;
        categories.forEach(this::addCategory);
        items.forEach(this::addItem);
    }

    // CONTENT
    public void updateContent(String name, String content) {
        this.name = name;
        this.content = content;
    }

    // STATUS
    public void changeStatus(PathStatus status) {
        this.status = status;
    }

    public void verifyThatPathHaveNoVerifiedStatus() {
        if (PathStatus.VERIFIED.equals(this.status)) {
            throw new PathException.PathWrongStatusException("Path must have a VERIFIED status, but currently have: " + this.status);
        }
    }

    // CATEGORY
    public void setCategories(List<AggregateId> newCategories) {
        List<AggregateId> categoriesToRemove = this.categories.stream()
                .map(PathHasCategory::getCategory)
                .filter(category -> !newCategories.contains(category))
                .collect(Collectors.toList());
        List<AggregateId> categoriesToAdd = newCategories.stream()
                .filter(newCategory -> getCategory(newCategory).isEmpty())
                .collect(Collectors.toList());
        removeCategories(categoriesToRemove);
        addCategories(categoriesToAdd);
    }

    public void addCategories(List<AggregateId> categories) {
        categories.forEach(this::addCategory);
    }

    public void addCategory(AggregateId category) {
        if (getCategory(category).isPresent()) {
            return;
        }
        PathHasCategory newPathHasCategory = new PathHasCategory(category);
        newPathHasCategory.setPath(this);
        this.categories.add(newPathHasCategory);
    }

    public void removeCategories(List<AggregateId> categories) {
        categories.forEach(this::removeCategory);
    }

    public void removeCategory(AggregateId category) {
        getCategory(category)
                .ifPresent(pathHasCategory -> {
                    pathHasCategory.setPath(null);
                    this.categories.remove(pathHasCategory);
                });
    }

    public Optional<PathHasCategory> getCategory(AggregateId category) {
        return this.categories.stream()
                .filter(pathHasCategory -> Objects.equals(pathHasCategory.getCategory(), category))
                .findAny();
    }


    // ITEMS

    public void setItems(List<PathItem> newPathItems) {
        List<PathItem> itemsToRemove = this.items.stream()
                .filter(existingItem -> !newPathItems.contains(existingItem))
                .toList();
        List<PathItem> itemsToAdd = newPathItems.stream()
                .filter(newItem -> newItem.getId() == null || getItem(newItem.getId()).isEmpty())
                .toList();

        itemsToRemove.forEach(i -> removeItem(i.getId()));
        itemsToAdd.forEach(this::addItem);
        MergerUtils.mergeCollections(Join.INNER, this.items, newPathItems, PathItem::merge, PathItem::getId);
    }

    public void addItem(PathItem item) {
        if (item.getId() != null && getItem(item.getId()).isPresent()) {
            return;
        }
        if (!new PathSpecification.UniqueEntitiesInPathItems(getItems()).isSatisfiedBy(item)) {
            if (ItemEntityType.GROUP.equals(item.getEntityType())) {
                throw new PathException.DuplicatedGroupsInOnePathException();
            }
            throw new PathException.DuplicatedTopicsInOnePathException();
        }
        item.setPath(this);
        this.items.add(item);
    }

    public void removeItem(UUID itemId) {
        getItem(itemId).ifPresent(item -> {
            item.setPath(null);
            this.items.remove(item);
        });
    }


    public Optional<PathItem> getItem(UUID itemId) {
        return this.items.stream()
                .filter(item -> Objects.equals(item.getId(), itemId))
                .findAny();
    }

    public void setCommentContainer(AggregateId commentContainer) {
        this.commentContainer = commentContainer;
    }

    public void setProgressContainer(AggregateId progressContainer) {
        this.progressContainer = progressContainer;
    }

    public void setVoting(AggregateId voting) {
        this.voting = voting;
    }
}

