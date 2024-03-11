package pl.app.authorization.role.query.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import pl.app.common.model.BaseAuditEntity;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Immutable
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_role")
public class RoleQuery extends BaseAuditEntity<RoleQuery, UUID> {
    @Id
    private UUID id;
    @Column(name = "role_name")
    private String name;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "role")
    private Set<RoleHasPermissionQuery> permissions = new LinkedHashSet<>();
}
