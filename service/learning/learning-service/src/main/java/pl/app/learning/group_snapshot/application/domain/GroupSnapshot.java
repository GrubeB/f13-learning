package pl.app.learning.group_snapshot.application.domain;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.app.common.ddd.BaseJpaSnapshotDomainEntity;
import pl.app.learning.group.application.domain.Group;
import pl.app.learning.group.application.domain.GroupStatus;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "t_group_snapshot")
public class GroupSnapshot extends BaseJpaSnapshotDomainEntity<Group, GroupSnapshot> {
    @Column(name = "topic_name", nullable = false)
    private String name;
    @Column(name = "topic_content", length = 8000)
    private String content;
    @Enumerated(EnumType.STRING)
    @Column(name = "topic_status", nullable = false)
    private GroupStatus status;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @Builder.Default
    private Set<GroupHasCategorySnapshot> categories = new LinkedHashSet<>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @Builder.Default
    private Set<GroupHasTopicSnapshot> topics = new LinkedHashSet<>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @Builder.Default
    private Set<GroupHasGroupSnapshot> groups = new LinkedHashSet<>();

    public void setCategories(Set<GroupHasCategorySnapshot> categories) {
        if (this.categories != categories) {
            this.categories.forEach(e -> e.setGroup(null));
            this.categories.clear();
            categories.forEach(this::addCategory);
        }
    }

    public void addCategory(GroupHasCategorySnapshot categorySnapshot) {
        this.categories.add(categorySnapshot);
        categorySnapshot.setGroup(this);
    }

    public void setTopics(Set<GroupHasTopicSnapshot> topics) {
        if (this.topics != topics) {
            this.topics.forEach(e -> e.setGroup(null));
            this.topics.clear();
            topics.forEach(this::addTopic);
        }
    }

    public void addTopic(GroupHasTopicSnapshot topicSnapshot) {
        this.topics.add(topicSnapshot);
        topicSnapshot.setGroup(this);
    }

    public void setGroups(Set<GroupHasGroupSnapshot> groups) {
        if (this.groups != groups) {
            this.groups.forEach(e -> e.setGroup(null));
            this.groups.clear();
            groups.forEach(this::addGroup);
        }
    }

    public void addGroup(GroupHasGroupSnapshot groupSnapshot) {
        this.groups.add(groupSnapshot);
        groupSnapshot.setGroup(this);
    }

    @Override
    public void setSnapshotNumber(Long snapshotNumber) {
        this.snapshotNumber = snapshotNumber;
        this.categories.forEach(e -> e.setSnapshotNumber(snapshotNumber));
        this.topics.forEach(e -> e.setSnapshotNumber(snapshotNumber));
        this.groups.forEach(e -> e.setSnapshotNumber(snapshotNumber));
    }
}

