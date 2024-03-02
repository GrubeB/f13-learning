package pl.app.learning.group.application.domain;


import jakarta.persistence.*;
import lombok.Getter;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.BaseJpaSnapshotableDomainAggregateRoot;
import pl.app.common.ddd.annotation.AggregateRootAnnotation;
import pl.app.common.mapper.Join;
import pl.app.common.mapper.MergerUtils;
import pl.app.common.model.revision.Revisionable;
import pl.app.learning.group.application.domain.snapshot.*;
import pl.app.learning.group_revision.application.domain.GroupHasCategoryRevision;
import pl.app.learning.group_revision.application.domain.GroupHasGroupRevision;
import pl.app.learning.group_revision.application.domain.GroupHasTopicRevision;
import pl.app.learning.group_revision.application.domain.GroupRevision;

import java.util.*;
import java.util.stream.Collectors;

@AggregateRootAnnotation
@Entity
@Getter
@Table(name = "t_group")
public class Group extends BaseJpaSnapshotableDomainAggregateRoot<Group, GroupSnapshot> implements
        Revisionable<Group, UUID, GroupRevision, UUID> {
    @Column(name = "topic_name", nullable = false)
    private String name;
    @Column(name = "topic_content", length = 8000)
    private String content;
    @Enumerated(EnumType.STRING)
    @Column(name = "topic_status", nullable = false)
    private GroupStatus status;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<GroupHasCategory> categories = new LinkedHashSet<>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<GroupHasReference> references = new LinkedHashSet<>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<GroupHasTopic> topics = new LinkedHashSet<>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<GroupHasGroup> groups = new LinkedHashSet<>();

    @SuppressWarnings("unused")
    protected Group() {
        super();
    }

    public Group(String name,
                 String content,
                 GroupStatus status,
                 Set<AggregateId> categories,
                 Set<AggregateId> topics,
                 Set<AggregateId> groups) {
        this.name = name;
        this.content = content;
        this.status = status;
        categories.forEach(this::addCategory);
        topics.forEach(this::addTopic);
        groups.forEach(this::addGroup);
    }
    // CONTENT

    public void updateContent(String name, String content) {
        this.verifyIsInDraftStatus();
        this.name = name;
        this.content = content;
    }

    // STATUS
    public void changeStatus(GroupStatus status) {
        this.status = status;
    }

    public void verifyIsInDraftStatus() {
        if (!GroupStatus.DRAFT.equals(this.status)) {
            throw new GroupException.GroupWrongStatusException("Group must be in Draft status, but is in: " + this.status);
        }
    }

    // CATEGORY
    public void setCategories(Set<AggregateId> categories) {
        this.categories.clear();
        categories.forEach(this::addCategory);
    }

    public void addCategory(AggregateId category) {
        if (getCategory(category).isPresent()) {
            return;
        }
        this.categories.add(new GroupHasCategory(this, category));
    }

    public void removeCategory(AggregateId category) {
        getCategory(category)
                .ifPresent(groupHasCategory -> this.categories.remove(groupHasCategory));
    }

    public Optional<GroupHasCategory> getCategory(AggregateId category) {
        return this.categories.stream()
                .filter(e -> Objects.equals(e.getCategory(), category))
                .findAny();
    }

    // REFERENCE
    public void setReferences(Set<AggregateId> references) {
        this.references.clear();
        references.forEach(this::addReference);
    }

    public void addReference(AggregateId reference) {
        if (getReference(reference).isPresent()) {
            return;
        }
        this.references.add(new GroupHasReference(this, reference));
    }

    public void removeReference(AggregateId reference) {
        getReference(reference)
                .ifPresent(groupHasReference -> this.references.remove(groupHasReference));
    }

    public Optional<GroupHasReference> getReference(AggregateId reference) {
        return this.references.stream()
                .filter(e -> Objects.equals(e.getReference(), reference))
                .findAny();
    }

    // TOPIC
    public void setTopics(Set<AggregateId> topics) {
        this.topics.clear();
        topics.forEach(this::addTopic);
    }

    public void addTopic(AggregateId topic) {
        if (getTopic(topic).isPresent()) {
            return;
        }
        this.topics.add(new GroupHasTopic(this, topic));
    }

    public void removeTopic(AggregateId topic) {
        getTopic(topic)
                .ifPresent(groupHasTopic -> this.topics.remove(groupHasTopic));
    }

    public Optional<GroupHasTopic> getTopic(AggregateId topic) {
        return this.topics.stream()
                .filter(e -> Objects.equals(e.getTopic(), topic))
                .findAny();
    }

    // GROUP
    public void setGroups(Set<AggregateId> groups) {
        this.groups.clear();
        groups.forEach(this::addGroup);
    }

    public void addGroup(AggregateId group) {
        if (getGroup(group).isPresent()) {
            return;
        }
        this.groups.add(new GroupHasGroup(this, group));
    }

    public void removeGroup(AggregateId group) {
        getGroup(group)
                .ifPresent(groupHasGroup -> this.groups.remove(groupHasGroup));
    }

    public Optional<GroupHasGroup> getGroup(AggregateId group) {
        return this.groups.stream()
                .filter(e -> Objects.equals(e.getChildGroup(), group))
                .findAny();
    }

    @Override
    public GroupSnapshot makeSnapshot() {
        var categorySnapshots = this.categories.stream().map(e -> new GroupHasCategorySnapshot(e, e.getCategory())).collect(Collectors.toSet());
        var referenceSnapshots = this.references.stream().map(e -> new GroupHasReferenceSnapshot(e, e.getReference())).collect(Collectors.toSet());
        var topicSnapshots = this.topics.stream().map(e -> new GroupHasTopicSnapshot(e, e.getTopic())).collect(Collectors.toSet());
        var groupSnapshots = this.groups.stream().map(e -> new GroupHasGroupSnapshot(e, e.getChildGroup())).collect(Collectors.toSet());
        return new GroupSnapshot(this, this.name, this.content, this.status, categorySnapshots, referenceSnapshots, topicSnapshots, groupSnapshots);
    }

    @Override
    public Group revertSnapshot(GroupSnapshot snapshot) {
        this.name = snapshot.getName();
        this.content = snapshot.getContent();
        this.status = snapshot.getStatus();
        MergerUtils.mergeCollections(Join.RIGHT, this.categories, snapshot.getCategories(),
                (e, s) -> e.revertSnapshot(this, s), GroupHasCategory::new,
                GroupHasCategory::getId, GroupHasCategorySnapshot::getSnapshotOwnerId);
        MergerUtils.mergeCollections(Join.RIGHT, this.references, snapshot.getReferences(),
                (e, s) -> e.revertSnapshot(this, s), GroupHasReference::new,
                GroupHasReference::getId, GroupHasReferenceSnapshot::getSnapshotOwnerId);
        MergerUtils.mergeCollections(Join.RIGHT, this.topics, snapshot.getTopics(),
                (e, s) -> e.revertSnapshot(this, s), GroupHasTopic::new,
                GroupHasTopic::getId, GroupHasTopicSnapshot::getSnapshotOwnerId);
        MergerUtils.mergeCollections(Join.RIGHT, this.groups, snapshot.getGroups(),
                (e, s) -> e.revertSnapshot(this, s), GroupHasGroup::new,
                GroupHasGroup::getId, GroupHasGroupSnapshot::getSnapshotOwnerId);
        return this;
    }

    @Override
    public Group mergeRevision(GroupRevision revision) {
        this.name = revision.getName();
        this.content = revision.getContent();
        MergerUtils.mergeCollections(Join.RIGHT, this.categories, revision.getCategories(),
                (e, s) -> e.mergeRevision(this, s), GroupHasCategory::new,
                GroupHasCategory::getId, GroupHasCategoryRevision::getRevisionOwnerId);
        MergerUtils.mergeCollections(Join.RIGHT, this.topics, revision.getTopics(),
                (e, s) -> e.mergeRevision(this, s), GroupHasTopic::new,
                GroupHasTopic::getId, GroupHasTopicRevision::getRevisionOwnerId);
        MergerUtils.mergeCollections(Join.RIGHT, this.groups, revision.getGroups(),
                (e, s) -> e.mergeRevision(this, s), GroupHasGroup::new,
                GroupHasGroup::getId, GroupHasGroupRevision::getRevisionOwnerId);
        return this;
    }
}

