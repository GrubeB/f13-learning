package pl.app.learning.group.application.domain;


import jakarta.persistence.*;
import lombok.Getter;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.BaseJpaAuditDomainEntity;
import pl.app.common.ddd.annotation.EntityAnnotation;

@EntityAnnotation
@Entity
@Getter
@Table(name = "t_group_has_reference")
public class GroupHasReference extends BaseJpaAuditDomainEntity<GroupHasReference> {

    @ManyToOne(optional = false)
    @JoinColumn(name = "group_id", nullable = false, updatable = false)
    private Group group;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "aggregateId", column = @Column(name = "reference_id", nullable = false, updatable = false))
    })
    private AggregateId reference;

    @SuppressWarnings("unused")
    protected GroupHasReference() {
        super();
    }

    public GroupHasReference(Group group, AggregateId reference) {
        this.group = group;
        this.reference = reference;
    }
}

