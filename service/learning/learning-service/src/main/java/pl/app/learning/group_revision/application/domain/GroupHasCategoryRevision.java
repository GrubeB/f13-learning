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
import pl.app.learning.group.application.domain.Group;
import pl.app.learning.group.application.domain.GroupHasCategory;
import pl.app.learning.group.application.domain.snapshot.GroupHasCategorySnapshot;

import java.util.UUID;

@EntityAnnotation
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_group_has_category_revision")
public class GroupHasCategoryRevision extends BaseRevisionEntity<GroupHasCategory, UUID, GroupHasCategoryRevision, UUID> {

    @ManyToOne(optional = false)
    @JoinColumn(name = "group_id", nullable = false, updatable = false)
    private GroupRevision group;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "aggregateId", column = @Column(name = "category_id", nullable = false, updatable = false))
    })
    private AggregateId category;
}

