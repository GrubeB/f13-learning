package pl.app.learning.group_revision.application.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.BaseJpaAuditDomainEntity;
import pl.app.common.ddd.annotation.EntityAnnotation;
import pl.app.common.model.BaseRevisionEntity;
import pl.app.learning.group.application.domain.GroupHasCategory;
import pl.app.learning.group.application.domain.GroupHasGroup;
import pl.app.learning.group.application.domain.snapshot.GroupHasGroupSnapshot;

import java.util.UUID;

@EntityAnnotation
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_group_has_group_revision")
public class GroupHasGroupRevision extends BaseRevisionEntity<GroupHasGroup, UUID, GroupHasGroupRevision, UUID> {

    @ManyToOne(optional = false)
    @JoinColumn(name = "group_id_1", nullable = false, updatable = false)
    private GroupRevision group;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "aggregateId", column = @Column(name = "group_id_2", nullable = false, updatable = false))
    })
    private AggregateId childGroup;
}

