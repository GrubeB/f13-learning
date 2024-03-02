package pl.app.learning.group_snapshot.application.domain;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.app.common.ddd.AggregateId;
import pl.app.common.model.BaseSnapshotEntity;
import pl.app.learning.group.application.domain.GroupHasReference;

import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "t_group_has_reference_snapshot")
public class GroupHasReferenceSnapshot extends BaseSnapshotEntity<GroupHasReference, UUID, GroupHasReferenceSnapshot> {

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "aggregateId", column = @Column(name = "reference_id", nullable = false, updatable = false))
    })
    private AggregateId reference;

    @ManyToOne(optional = false)
    @JoinColumn(name = "group_snapshot_id", nullable = false, updatable = false)
    @ToString.Exclude
    private GroupSnapshot group;
}

