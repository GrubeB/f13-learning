package pl.app.learning.group_revision.application.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.app.common.ddd.annotation.AggregateRootAnnotation;
import pl.app.common.model.BaseRevisionEntity;
import pl.app.learning.group.application.domain.Group;
import pl.app.learning.group.application.domain.GroupStatus;
import pl.app.learning.group.query.model.GroupQuery;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@AggregateRootAnnotation
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_group_revision")
public class GroupRevision extends BaseRevisionEntity<Group, UUID, GroupRevision, UUID> {
    @Column(name = "topic_name", nullable = false)
    private String name;
    @Column(name = "topic_content", length = 8000)
    private String content;
    @Enumerated(EnumType.STRING)
    @Column(name = "topic_status", nullable = false)
    private GroupStatus status;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<GroupHasCategoryRevision> categories = new LinkedHashSet<>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<GroupHasTopicRevision> topics = new LinkedHashSet<>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<GroupHasGroupRevision> groups = new LinkedHashSet<>();

    public GroupRevision(Group revisionOwner) {
        super(revisionOwner);
    }

    public void setCategories(Set<GroupHasCategoryRevision> categories) {
        if (this.categories != categories) {
            this.categories.forEach(e -> e.setGroup(null));
            this.categories.clear();
            categories.forEach(this::addCategory);
        }
    }

    public void addCategory(GroupHasCategoryRevision category) {
        this.categories.add(category);
        category.setGroup(this);
    }

    public void setTopics(Set<GroupHasTopicRevision> topics) {
        if (this.topics != topics) {
            this.topics.forEach(e -> e.setGroup(null));
            this.topics.clear();
            topics.forEach(this::addTopic);
        }
    }

    public void addTopic(GroupHasTopicRevision topic) {
        this.topics.add(topic);
        topic.setGroup(this);
    }

    public void setGroups(Set<GroupHasGroupRevision> groups) {
        if (this.groups != groups) {
            this.groups.forEach(e -> e.setGroup(null));
            this.groups.clear();
            groups.forEach(this::addGroup);
        }
    }

    public void addGroup(GroupHasGroupRevision group) {
        this.groups.add(group);
        group.setGroup(this);
    }
}

