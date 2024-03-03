package pl.app.learning.group.application.domain;


import jakarta.persistence.*;
import lombok.Getter;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.BaseJpaAuditDomainEntity;
import pl.app.common.ddd.annotation.EntityAnnotation;
import pl.app.learning.group_revision.application.domain.GroupHasCategoryRevision;
import pl.app.learning.group_snapshot.application.domain.GroupHasCategorySnapshot;

@EntityAnnotation
@Entity
@Getter
@Table(name = "t_group_has_category")
public class GroupHasCategory extends BaseJpaAuditDomainEntity<GroupHasCategory> {

    @ManyToOne(optional = false)
    @JoinColumn(name = "group_id", nullable = false, updatable = false)
    private Group group;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "aggregateId", column = @Column(name = "category_id", nullable = false, updatable = false))
    })
    private AggregateId category;

    @SuppressWarnings("unused")
    protected GroupHasCategory() {
        super();
    }

    public GroupHasCategory(Group group, AggregateId category) {
        this.group = group;
        this.category = category;
    }

    public GroupHasCategory revertSnapshot(Group group, GroupHasCategorySnapshot snapshot) {
        this.entityId = snapshot.getSnapshotOwnerId();
        this.group = group;
        this.category = snapshot.getCategory();
        return this;
    }

    public GroupHasCategory mergeRevision(Group group, GroupHasCategoryRevision revision) {
        this.entityId = revision.getRevisionOwnerId();
        this.group = group;
        this.category = revision.getCategory();
        return this;
    }
}

