package pl.app.authorization.permision.application.domain;

import jakarta.persistence.*;
import lombok.Getter;
import pl.app.authorization.permision.application.domain.event.PermissionCreatedEvent;
import pl.app.authorization.permision.application.domain.event.PermissionDeletedEvent;
import pl.app.common.ddd.BaseJpaAuditDomainAggregateRoot;
import pl.app.common.ddd.annotation.AggregateRootAnnotation;
import pl.app.common.ddd.event.DomainEventPublisher;

@AggregateRootAnnotation
@Entity
@Getter
@Table(name = "t_permission", uniqueConstraints = {
        @UniqueConstraint(name = "uc_permission_permission_name", columnNames = {"permission_name"})
})
public class Permission extends BaseJpaAuditDomainAggregateRoot<Permission> {
    @Column(name = "permission_name", nullable = false)
    private String name;

    @SuppressWarnings("unused")
    protected Permission() {
        super();
    }

    public Permission(String name, DomainEventPublisher eventPublisher) {
        super(eventPublisher);
        this.name = name;
        this.eventPublisher.publish(new PermissionCreatedEvent(this.getId(), this.name));
    }

    public void delete() {
        this.eventPublisher.publish(new PermissionDeletedEvent(this.getId(), this.name));
    }
}
