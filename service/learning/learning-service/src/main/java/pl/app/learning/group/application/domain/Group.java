package pl.app.learning.group.application.domain;


import jakarta.persistence.*;
import lombok.Getter;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.BaseJpaSnapshotableDomainAggregateRoot;
import pl.app.common.ddd.annotation.AggregateRootAnnotation;
import pl.app.common.mapper.Join;
import pl.app.common.mapper.MergerUtils;
import pl.app.common.model.revision.Revisionable;
import pl.app.learning.group_revision.application.domain.GroupHasCategoryRevision;
import pl.app.learning.group_revision.application.domain.GroupHasGroupRevision;
import pl.app.learning.group_revision.application.domain.GroupHasTopicRevision;
import pl.app.learning.group_revision.application.domain.GroupRevision;
import pl.app.learning.group_snapshot.application.domain.GroupHasCategorySnapshot;
import pl.app.learning.group_snapshot.application.domain.GroupHasGroupSnapshot;
import pl.app.learning.group_snapshot.application.domain.GroupHasTopicSnapshot;
import pl.app.learning.group_snapshot.application.domain.GroupSnapshot;

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
    private final Set<GroupHasCategory> categories = new LinkedHashSet<>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private final Set<GroupHasTopic> topics = new LinkedHashSet<>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private final Set<GroupHasGroup> groups = new LinkedHashSet<>();

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
            @AttributeOverride(name = "aggregateId", column = @Column(name = "reference_container_id"))
    })
    private AggregateId referenceContainer;

    @SuppressWarnings("unused")
    protected Group() {
        super();
    }

    public Group(String name,
                 String content,
                 GroupStatus status,
                 List<AggregateId> categories,
                 List<AggregateId> topics,
                 List<AggregateId> groups) {
        this.name = name;
        this.content = content;
        this.status = status;
        categories.forEach(this::addCategory);
        topics.forEach(this::addTopic);
        groups.forEach(this::addGroup);
    }
    // CONTENT

    public void updateContent(String name, String content) {
        this.name = name;
        this.content = content;
    }

    // STATUS
    public void changeStatus(GroupStatus status) {
        this.status = status;
    }

    public void verifyThatTopicHaveNoVerifiedStatus() {
        if (GroupStatus.VERIFIED.equals(this.status)) {
            throw new GroupException.GroupWrongStatusException("Group must have a VERIFIED status, but currently have: " + this.status);
        }
    }

    // CATEGORY
    public void setCategories(Set<AggregateId> newCategories) {
        List<AggregateId> categoriesToRemove = this.categories.stream()
                .map(GroupHasCategory::getCategory)
                .filter(e -> !newCategories.contains(e))
                .toList();
        List<AggregateId> categoriesToAdd = newCategories.stream()
                .filter(ne -> getCategory(ne).isEmpty())
                .toList();
        categoriesToRemove.forEach(this::removeCategory);
        categoriesToAdd.forEach(this::addCategory);
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

    // TOPIC
    public void setTopics(Set<AggregateId> newCategories) {
        List<AggregateId> topicsToRemove = this.topics.stream()
                .map(GroupHasTopic::getTopic)
                .filter(e -> !newCategories.contains(e))
                .toList();
        List<AggregateId> topicsToAdd = newCategories.stream()
                .filter(ne -> getTopic(ne).isEmpty())
                .toList();
        topicsToRemove.forEach(this::removeTopic);
        topicsToAdd.forEach(this::addTopic);
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
    public void setGroups(Set<AggregateId> newGroups) {
        List<AggregateId> groupsToRemove = this.groups.stream()
                .map(GroupHasGroup::getChildGroup)
                .filter(e -> !newGroups.contains(e))
                .toList();
        List<AggregateId> groupsToAdd = newGroups.stream()
                .filter(ne -> getGroup(ne).isEmpty())
                .toList();
        groupsToRemove.forEach(this::removeGroup);
        groupsToAdd.forEach(this::addGroup);
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

    public void setCommentContainer(AggregateId commentContainer) {
        this.commentContainer = commentContainer;
    }

    public void setReferenceContainer(AggregateId referenceContainer) {
        this.referenceContainer = referenceContainer;
    }

    public void setVoting(AggregateId voting) {
        this.voting = voting;
    }

    @Override
    public GroupSnapshot makeSnapshot() {
        GroupSnapshot snapshot = GroupSnapshot.builder()
                .snapshotOwnerId(this.getId())
                .name(this.name)
                .content(this.content)
                .status(this.status)
                .build();
        snapshot.setCategories(this.categories.stream().map(e ->
                        GroupHasCategorySnapshot.builder()
                                .snapshotOwnerId(e.getId())
                                .category(e.getCategory())
                                .build())
                .collect(Collectors.toSet()));
        snapshot.setGroups(this.groups.stream().map(e ->
                        GroupHasGroupSnapshot.builder()
                                .snapshotOwnerId(e.getId())
                                .childGroup(e.getChildGroup())
                                .build())
                .collect(Collectors.toSet()));
        snapshot.setTopics(this.topics.stream().map(e ->
                        GroupHasTopicSnapshot.builder()
                                .snapshotOwnerId(e.getId())
                                .topic(e.getTopic())
                                .build())
                .collect(Collectors.toSet()));
        return snapshot;
    }

    @Override
    public Group revertSnapshot(GroupSnapshot snapshot) {
        this.name = snapshot.getName();
        this.content = snapshot.getContent();
        this.status = snapshot.getStatus();
        MergerUtils.mergeCollections(Join.RIGHT, this.categories, snapshot.getCategories(),
                (e, s) -> e.revertSnapshot(this, s), GroupHasCategory::new,
                GroupHasCategory::getId, GroupHasCategorySnapshot::getSnapshotOwnerId);
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

