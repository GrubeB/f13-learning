package pl.app.authorization.role.application.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import pl.app.authorization.role.application.domain.event.RoleCreatedEvent;
import pl.app.authorization.role.application.domain.event.RoleDeletedEvent;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.BaseJpaAuditDomainAggregateRoot;
import pl.app.common.ddd.annotation.AggregateRootAnnotation;
import pl.app.common.ddd.event.DomainEventPublisher;

import java.util.*;

@AggregateRootAnnotation
@Entity
@Getter
@Table(name = "t_role", uniqueConstraints = {
        @UniqueConstraint(name = "uc_role_role_name", columnNames = {"role_name"})
})
public class Role extends BaseJpaAuditDomainAggregateRoot<Role> {
    @Column(name = "role_name", nullable = false)
    private String name;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "role", orphanRemoval = true)
    @Builder.Default
    private Set<RoleHasPermission> permissions = new LinkedHashSet<>();

    @SuppressWarnings("unused")
    protected Role() {
        super();
    }

    public Role(String name, List<AggregateId> permissions, DomainEventPublisher eventPublisher) {
        super(eventPublisher);
        this.name = name;
        setPermissions(permissions);
        this.eventPublisher.publish(new RoleCreatedEvent(this.getId(), this.name));
    }

    public void setPermissions(Collection<AggregateId> newPermissions) {
        this.permissions.forEach(e -> removePermission(e.getPermission()));
        newPermissions.forEach(this::addPermission);
    }

    public void addPermission(AggregateId permission) {
        if (getPermissions(permission).isPresent()) {
            return;
        }
        this.permissions.add(new RoleHasPermission(this, permission));
    }

    public void removePermission(AggregateId permission) {
        getPermissions(permission)
                .ifPresent(e -> {
                    e.setRole(null);
                    this.permissions.remove(e);
                });
    }

    public Optional<RoleHasPermission> getPermissions(AggregateId permission) {
        return this.permissions.stream()
                .filter(e -> Objects.equals(e.getPermission(), permission))
                .findAny();
    }

    public void delete() {
        this.eventPublisher.publish(new RoleDeletedEvent(this.getId(), this.name));
    }
}
