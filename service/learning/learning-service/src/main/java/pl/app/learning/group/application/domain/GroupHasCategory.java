package pl.app.learning.group.application.domain;


import jakarta.persistence.*;
import lombok.Getter;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.BaseJpaAuditDomainEntity;
import pl.app.common.ddd.annotation.EntityAnnotation;

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
}

