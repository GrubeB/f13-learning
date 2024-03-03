package pl.app.learning.group_snapshot.application.domain;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
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
@SuperBuilder
@Table(name = "t_group_has_category_snapshot")
public class GroupHasCategorySnapshot extends BaseSnapshotEntity<GroupHasCategory, UUID, GroupHasCategorySnapshot> {

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "aggregateId", column = @Column(name = "category_id", nullable = false, updatable = false))
    })
    private AggregateId category;

    @ManyToOne(optional = false)
    @JoinColumn(name = "group_snapshot_id", nullable = false, updatable = false)
    @ToString.Exclude
    private GroupSnapshot group;
}

