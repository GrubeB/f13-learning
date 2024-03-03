package pl.app.learning.group.application.domain;


import jakarta.persistence.*;
import lombok.Getter;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.BaseJpaAuditDomainEntity;
import pl.app.common.ddd.annotation.EntityAnnotation;
import pl.app.learning.group_revision.application.domain.GroupHasTopicRevision;
import pl.app.learning.group_snapshot.application.domain.GroupHasTopicSnapshot;

@EntityAnnotation
@Entity
@Getter
@Table(name = "t_group_has_topic")
public class GroupHasTopic extends BaseJpaAuditDomainEntity<GroupHasTopic> {

    @ManyToOne(optional = false)
    @JoinColumn(name = "group_id", nullable = false, updatable = false)
    private Group group;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "aggregateId", column = @Column(name = "topic_id", nullable = false, updatable = false))
    })
    private AggregateId topic;

    @SuppressWarnings("unused")
    protected GroupHasTopic() {
        super();
    }

    public GroupHasTopic(Group group, AggregateId topic) {
        this.group = group;
        this.topic = topic;
    }

    public GroupHasTopic revertSnapshot(Group group, GroupHasTopicSnapshot snapshot) {
        this.entityId = snapshot.getSnapshotOwnerId();
        this.group = group;
        this.topic = snapshot.getTopic();
        return this;
    }

    public GroupHasTopic mergeRevision(Group group, GroupHasTopicRevision revision) {
        this.entityId = revision.getRevisionOwnerId();
        this.group = group;
        this.topic = revision.getTopic();
        return this;
    }
}

