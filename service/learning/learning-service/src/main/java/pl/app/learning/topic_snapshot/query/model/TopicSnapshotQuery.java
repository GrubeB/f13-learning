package pl.app.learning.topic_snapshot.query.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import pl.app.common.model.BaseAuditEntity;
import pl.app.learning.topic.application.domain.TopicStatus;

import java.util.UUID;

@Immutable
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_topic_snapshot")
public class TopicSnapshotQuery extends BaseAuditEntity<TopicSnapshotQuery,UUID> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "snapshot_id", nullable = false, updatable = false)
    protected UUID id;

    @Column(name = "snapshot_number", nullable = false, updatable = false)
    protected Long snapshotNumber;

    @Column(name = "owner_id")
    protected UUID snapshotOwnerId;
    @Column(name = "topic_name")
    private String name;
    @Column(name = "topic_content")
    private String content;
    @Enumerated(EnumType.STRING)
    @Column(name = "topic_status")
    private TopicStatus status;
}

