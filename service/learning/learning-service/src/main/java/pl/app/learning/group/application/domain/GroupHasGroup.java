package pl.app.learning.group.application.domain;


import jakarta.persistence.*;
import lombok.Getter;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.BaseJpaAuditDomainEntity;
import pl.app.common.ddd.annotation.EntityAnnotation;
import pl.app.learning.group_revision.application.domain.GroupHasGroupRevision;
import pl.app.learning.group_snapshot.application.domain.GroupHasGroupSnapshot;

@EntityAnnotation
@Entity
@Getter
@Table(name = "t_group_has_group")
public class GroupHasGroup extends BaseJpaAuditDomainEntity<GroupHasGroup> {

    @ManyToOne(optional = false)
    @JoinColumn(name = "group_id_1", nullable = false, updatable = false)
    private Group group;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "aggregateId", column = @Column(name = "group_id_2", nullable = false, updatable = false))
    })
    private AggregateId childGroup;

    @SuppressWarnings("unused")
    protected GroupHasGroup() {
        super();
    }

    public GroupHasGroup(Group group, AggregateId childGroup) {
        this.group = group;
        this.childGroup = childGroup;
    }

    public GroupHasGroup revertSnapshot(Group group, GroupHasGroupSnapshot snapshot) {
        this.entityId = snapshot.getSnapshotOwnerId();
        this.group = group;
        this.childGroup = snapshot.getChildGroup();
        return this;
    }

    public GroupHasGroup mergeRevision(Group group, GroupHasGroupRevision revision) {
        this.entityId = revision.getRevisionOwnerId();
        this.group = group;
        this.childGroup = revision.getChildGroup();
        return this;
    }
}

