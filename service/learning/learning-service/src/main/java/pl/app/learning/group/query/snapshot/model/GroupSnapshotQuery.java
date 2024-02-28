package pl.app.learning.group.query.snapshot.model;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;
import pl.app.common.model.BaseAuditEntity;
import pl.app.learning.group.application.domain.GroupStatus;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Immutable
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_group_snapshot")
public class GroupSnapshotQuery extends BaseAuditEntity<GroupSnapshotQuery, UUID> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "snapshot_id", nullable = false, updatable = false)
    protected UUID id;
    @Column(name = "snapshot_number", nullable = false, updatable = false)
    protected Long snapshotNumber;
    @Column(name = "owner_id")
    protected UUID snapshotOwnerId;

    @Column(name = "topic_name", nullable = false)
    private String name;
    @Column(name = "topic_content", length = 8000)
    private String content;
    @Enumerated(EnumType.STRING)
    @Column(name = "topic_status", nullable = false)
    private GroupStatus status;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<GroupHasCategorySnapshotQuery> categories = new LinkedHashSet<>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<GroupHasReferenceSnapshotQuery> references = new LinkedHashSet<>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<GroupHasTopicSnapshotQuery> topics = new LinkedHashSet<>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<GroupHasGroupSnapshotQuery> groups = new LinkedHashSet<>();

}

