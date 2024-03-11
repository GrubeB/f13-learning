package pl.app.authorization.role.application.domain;

import jakarta.persistence.*;
import lombok.Getter;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.BaseJpaAuditDomainEntity;
import pl.app.common.ddd.annotation.EntityAnnotation;

@EntityAnnotation
@Entity
@Getter
@Table(name = "t_role_has_permission")
public class RoleHasPermission extends BaseJpaAuditDomainEntity<RoleHasPermission> {
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "aggregateId", column = @Column(name = "permission_id", nullable = false, updatable = false))
    })
    private AggregateId permission;

    @ManyToOne(optional = false)
    @JoinColumn(name = "role_id", updatable = false, nullable = false)
    private Role role;

    @SuppressWarnings("unused")
    protected RoleHasPermission() {
        super();
    }

    public RoleHasPermission(Role role, AggregateId permission) {
        this.permission = permission;
        this.role = role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
