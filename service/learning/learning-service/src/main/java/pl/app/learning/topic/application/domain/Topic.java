package pl.app.learning.topic.application.domain;


import jakarta.persistence.*;
import lombok.Getter;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.BaseJpaSnapshotableDomainAggregateRoot;
import pl.app.common.ddd.annotation.AggregateRootAnnotation;
import pl.app.common.mapper.Join;
import pl.app.common.mapper.MergerUtils;
import pl.app.common.model.revision.Revisionable;
import pl.app.learning.topic_revision.application.domain.TopicHasCategoryRevision;
import pl.app.learning.topic_revision.application.domain.TopicRevision;
import pl.app.learning.topic_snapshot.domain.model.TopicHasCategorySnapshot;
import pl.app.learning.topic_snapshot.domain.model.TopicSnapshot;

import java.util.*;
import java.util.stream.Collectors;

@AggregateRootAnnotation
@Entity
@Getter
@Table(name = "t_topic")
public class Topic extends BaseJpaSnapshotableDomainAggregateRoot<Topic, TopicSnapshot> implements
        Revisionable<Topic, UUID, TopicRevision, UUID> {
    @Column(name = "topic_name")
    private String name;
    @Column(name = "topic_content")
    private String content;
    @Enumerated(EnumType.STRING)
    @Column(name = "topic_status")
    private TopicStatus status;
    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private final Set<TopicHasCategory> categories = new LinkedHashSet<>();
    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private final Set<TopicHasReference> references = new LinkedHashSet<>();

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

    @SuppressWarnings("unused")
    protected Topic() {
        super();
    }

    public Topic(String name, String content, TopicStatus status, List<AggregateId> categories) {
        this.name = name;
        this.status = status;
        this.content = content;
        categories.forEach(this::addCategory);
    }
    // CONTENT

    public void updateContent(String name, String content) {
        this.name = name;
        this.content = content;
    }

    // STATUS
    public void changeStatus(TopicStatus status) {
        this.status = status;
    }

    public void verifyThatTopicHaveNoVerifiedStatus() {
        if (TopicStatus.VERIFIED.equals(this.status)) {
            throw new TopicException.TopicWrongStatusException("Topic must have a VERIFIED status, but currently have: " + this.status);
        }
    }
    // REFERENCE

    public void addReferences(List<AggregateId> references) {
        references.forEach(this::addReference);
    }

    public void addReference(AggregateId reference) {
        if (getReference(reference).isPresent()) {
            return;
        }
        TopicHasReference newTopicHasReference = new TopicHasReference(this, reference);
        this.references.add(newTopicHasReference);
    }

    public void removeReferences(List<AggregateId> references) {
        references.forEach(this::removeReference);
    }

    public void removeReference(AggregateId reference) {
        getReference(reference)
                .ifPresent(topicHasReference -> this.references.remove(topicHasReference));
    }


    public Optional<TopicHasReference> getReference(AggregateId reference) {
        return this.references.stream()
                .filter(topicHasReference -> Objects.equals(topicHasReference.getReference(), reference))
                .findAny();
    }

    // CATEGORY
    public void setCategories(List<AggregateId> newCategories) {
        List<AggregateId> categoriesToRemove = this.categories.stream()
                .map(TopicHasCategory::getCategory)
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
        TopicHasCategory newTopicHasCategory = new TopicHasCategory(this, category);
        this.categories.add(newTopicHasCategory);
    }

    public void removeCategories(List<AggregateId> categories) {
        categories.forEach(this::removeCategory);
    }

    public void removeCategory(AggregateId category) {
        getCategory(category)
                .ifPresent(topicHasCategory -> this.categories.remove(topicHasCategory));
    }

    public Optional<TopicHasCategory> getCategory(AggregateId category) {
        return this.categories.stream()
                .filter(topicHasCategory -> Objects.equals(topicHasCategory.getCategory(), category))
                .findAny();
    }

    public void setCommentContainer(AggregateId commentContainer){
        this.commentContainer = commentContainer;
    }

    public void setVoting(AggregateId voting) {
        this.voting = voting;
    }

    @Override
    public TopicSnapshot makeSnapshot() {
        TopicSnapshot snapshot = TopicSnapshot.builder()
                .snapshotOwnerId(this.getId())
                .name(this.name)
                .content(this.content)
                .status(this.status)
                .build();
        snapshot.setCategories(this.categories.stream().map(e ->
                        TopicHasCategorySnapshot.builder()
                                .snapshotOwnerId(e.getId())
                                .category(e.getCategory())
                                .build())
                .collect(Collectors.toSet()));
        return snapshot;
    }

    @Override
    public Topic revertSnapshot(TopicSnapshot snapshot) {
        this.name = snapshot.getName();
        this.content = snapshot.getContent();
        this.status = snapshot.getStatus();
        MergerUtils.mergeCollections(Join.RIGHT, this.categories, snapshot.getCategories(),
                (e, s) -> e.revertSnapshot(this, s), TopicHasCategory::new,
                TopicHasCategory::getId, TopicHasCategorySnapshot::getSnapshotOwnerId);
        return this;
    }

    @Override
    public Topic mergeRevision(TopicRevision revision) {
        this.name = revision.getName();
        this.content = revision.getContent();
        MergerUtils.mergeCollections(Join.RIGHT, this.categories, revision.getCategories(),
                (e, s) -> e.mergeRevision(this, s), TopicHasCategory::new,
                TopicHasCategory::getId, TopicHasCategoryRevision::getRevisionOwnerId);
        return this;
    }
}

