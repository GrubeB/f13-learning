package pl.app.learning.group.application.domain.snapshot;


import jakarta.persistence.*;
import lombok.*;
import pl.app.common.ddd.AggregateId;
import pl.app.common.model.BaseSnapshotEntity;
import pl.app.learning.group.application.domain.GroupHasCategory;

import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_group_has_category_snapshot")
public class GroupHasCategorySnapshot extends BaseSnapshotEntity<GroupHasCategory, UUID, GroupHasCategorySnapshot> {

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "aggregateId", column = @Column(name = "category_id", nullable = false, updatable = false))
    })
    private AggregateId category;

    @ManyToOne(optional = false)
    @JoinColumn(name = "group_snapshot_id", nullable = false, updatable = false)
    private GroupSnapshot group;

    public GroupHasCategorySnapshot(GroupHasCategory snapshotOwnerId, AggregateId category) {
        super(snapshotOwnerId);
        this.category = category;
    }
}

