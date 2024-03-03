package pl.app.learning.group_snapshot.query.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;
import pl.app.common.ddd.AggregateId;
import pl.app.common.model.BaseAuditEntity;

import java.util.UUID;

@Immutable
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_group_has_group_snapshot")
public class GroupHasGroupSnapshotQuery extends BaseAuditEntity<GroupHasGroupSnapshotQuery, UUID> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "snapshot_id", nullable = false, updatable = false)
    protected UUID id;
    @Column(name = "snapshot_number", nullable = false, updatable = false)
    protected Long snapshotNumber;
    @Column(name = "owner_id")
    protected UUID snapshotOwnerId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "aggregateId", column = @Column(name = "group_id_2", nullable = false, updatable = false))
    })
    private AggregateId childGroup;

    @ManyToOne(optional = false)
    @JoinColumn(name = "group_snapshot_id", nullable = false, updatable = false)
    @JsonIgnore
    @ToString.Exclude
    private GroupSnapshotQuery group;
}

