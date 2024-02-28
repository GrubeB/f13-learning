package pl.app.learning.group.application.domain.snapshot;


import jakarta.persistence.*;
import lombok.*;
import pl.app.common.ddd.AggregateId;
import pl.app.common.model.BaseSnapshotEntity;
import pl.app.learning.group.application.domain.GroupHasTopic;

import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_group_has_topic_snapshot")
public class GroupHasTopicSnapshot extends BaseSnapshotEntity<GroupHasTopic, UUID, GroupHasTopicSnapshot> {
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "aggregateId", column = @Column(name = "topic_id", nullable = false, updatable = false))
    })
    private AggregateId topic;

    @ManyToOne(optional = false)
    @JoinColumn(name = "group_snapshot_id", nullable = false, updatable = false)
    private GroupSnapshot group;

    public GroupHasTopicSnapshot(GroupHasTopic snapshotOwnerId, AggregateId topic) {
        super(snapshotOwnerId);
        this.topic = topic;
    }
}

