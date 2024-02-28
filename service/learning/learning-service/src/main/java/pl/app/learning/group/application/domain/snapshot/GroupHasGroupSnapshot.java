package pl.app.learning.group.application.domain.snapshot;


import jakarta.persistence.*;
import lombok.*;
import pl.app.common.ddd.AggregateId;
import pl.app.common.model.BaseSnapshotEntity;
import pl.app.learning.group.application.domain.GroupHasGroup;

import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_group_has_group_snapshot")
public class GroupHasGroupSnapshot extends BaseSnapshotEntity<GroupHasGroup, UUID, GroupHasGroupSnapshot> {

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "aggregateId", column = @Column(name = "group_id_2", nullable = false, updatable = false))
    })
    private AggregateId childGroup;

    @ManyToOne(optional = false)
    @JoinColumn(name = "group_snapshot_id", nullable = false, updatable = false)
    private GroupSnapshot group;

    public GroupHasGroupSnapshot(GroupHasGroup snapshotOwnerId, AggregateId childGroup) {
        super(snapshotOwnerId);
        this.childGroup = childGroup;
    }
}

