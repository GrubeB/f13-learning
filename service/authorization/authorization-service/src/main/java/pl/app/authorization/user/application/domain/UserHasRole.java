package pl.app.authorization.user.application.domain;

import jakarta.persistence.*;
import lombok.Getter;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.BaseJpaAuditDomainEntity;
import pl.app.common.ddd.annotation.EntityAnnotation;

@EntityAnnotation
@Entity
@Getter
@Table(name = "t_user_has_role")
public class UserHasRole extends BaseJpaAuditDomainEntity<UserHasRole> {
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "aggregateId", column = @Column(name = "role_id", nullable = false, updatable = false))
    })
    private AggregateId role;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", updatable = false, nullable = false)
    private User user;

    @SuppressWarnings("unused")
    protected UserHasRole() {
        super();
    }

    public UserHasRole(User user, AggregateId role) {
        this.role = role;
        this.user = user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
