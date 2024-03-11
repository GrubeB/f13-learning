package pl.app.authorization.role.query.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;
import pl.app.authorization.permision.query.model.PermissionQuery;
import pl.app.common.model.BaseAuditEntity;

import java.util.UUID;

@Immutable
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_role_has_permission")
public class RoleHasPermissionQuery extends BaseAuditEntity<RoleHasPermissionQuery, UUID> {
    @Id
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "permission_id")
    private PermissionQuery permission;

    @ManyToOne
    @JoinColumn(name = "role_id")
    @ToString.Exclude
    @JsonIgnore
    private RoleQuery role;
}
