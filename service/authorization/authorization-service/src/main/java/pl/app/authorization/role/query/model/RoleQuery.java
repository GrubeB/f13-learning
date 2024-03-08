package pl.app.authorization.role.query.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;
import pl.app.common.ddd.BaseJpaAuditDomainAggregateRoot;
import pl.app.common.ddd.annotation.AggregateRootAnnotation;
import pl.app.common.model.BaseAuditEntity;

import java.util.*;

@Immutable
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_role")
public class RoleQuery  extends BaseAuditEntity<RoleQuery, UUID> {
    @Id
    private UUID id;
    @Column(name = "role_name")
    private String name;
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "role")
    @Builder.Default
    private Set<RoleHasPermissionQuery> permissions = new LinkedHashSet<>();
}
