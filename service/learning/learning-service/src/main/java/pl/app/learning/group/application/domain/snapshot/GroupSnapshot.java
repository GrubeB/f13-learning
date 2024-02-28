package pl.app.learning.group.application.domain.snapshot;


import jakarta.persistence.*;
import lombok.*;
import pl.app.common.ddd.BaseJpaSnapshotDomainEntity;
import pl.app.common.ddd.annotation.EntityAnnotation;
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
    private Set<GroupHasCategorySnapshot> categories = new LinkedHashSet<>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<GroupHasReferenceSnapshot> references = new LinkedHashSet<>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<GroupHasTopicSnapshot> topics = new LinkedHashSet<>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<GroupHasGroupSnapshot> groups = new LinkedHashSet<>();

    public GroupSnapshot(Group snapshotOwnerId, String name, String content, GroupStatus status,
                         Set<GroupHasCategorySnapshot> categories,
                         Set<GroupHasReferenceSnapshot> references,
                         Set<GroupHasTopicSnapshot> topics,
                         Set<GroupHasGroupSnapshot> groups) {
        super(snapshotOwnerId);
        this.name = name;
        this.content = content;
        this.status = status;
        categories.forEach(this::addCategory);
        references.forEach(this::addReference);
        topics.forEach(this::addTopic);
        groups.forEach(this::addGroup);
    }

    public void addCategory(GroupHasCategorySnapshot categorySnapshot) {
        this.categories.add(categorySnapshot);
        categorySnapshot.setGroup(this);
    }

    public void addReference(GroupHasReferenceSnapshot referenceSnapshot) {
        this.references.add(referenceSnapshot);
        referenceSnapshot.setGroup(this);
    }

    public void addTopic(GroupHasTopicSnapshot topicSnapshot) {
        this.topics.add(topicSnapshot);
        topicSnapshot.setGroup(this);
    }

    public void addGroup(GroupHasGroupSnapshot groupSnapshot) {
        this.groups.add(groupSnapshot);
        groupSnapshot.setGroup(this);
    }
}

