package pl.app.learning.topic_snapshot.query.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;
import pl.app.common.ddd.AggregateId;
import pl.app.common.model.BaseAuditEntity;
import pl.app.learning.group_snapshot.query.model.GroupSnapshotQuery;

import java.util.UUID;

@Immutable
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_topic_has_category_snapshot")
public class TopicHasCategorySnapshotQuery extends BaseAuditEntity<TopicHasCategorySnapshotQuery, UUID> {
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
            @AttributeOverride(name = "aggregateId", column = @Column(name = "category_id", nullable = false, updatable = false))
    })
    private AggregateId category;

    @ManyToOne(optional = false)
    @JoinColumn(name = "topic_id", nullable = false, updatable = false)
    @JsonIgnore
    @ToString.Exclude
    private TopicSnapshotQuery topic;
}

