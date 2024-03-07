package pl.app.authorization.permision.query.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import pl.app.common.ddd.BaseJpaAuditDomainAggregateRoot;
import pl.app.common.ddd.annotation.AggregateRootAnnotation;
import pl.app.common.ddd.event.DomainEventPublisher;
import pl.app.common.model.BaseAuditEntity;

import java.util.UUID;

@Immutable
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_permission")
public class PermissionQuery extends BaseAuditEntity<PermissionQuery, UUID> {
    @Id
    private UUID id;
    @Enumerated(EnumType.STRING)
    @Column(name = "permission_name", nullable = false)
    private String name;
}
