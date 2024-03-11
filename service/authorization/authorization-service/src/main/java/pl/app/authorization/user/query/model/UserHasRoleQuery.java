package pl.app.authorization.user.query.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import pl.app.authorization.role.query.model.RoleQuery;
import pl.app.common.model.BaseAuditEntity;

import java.util.UUID;

@Immutable
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_user_has_role")
public class UserHasRoleQuery extends BaseAuditEntity<UserHasRoleQuery, UUID> {
    @Id
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleQuery role;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserQuery user;
}
