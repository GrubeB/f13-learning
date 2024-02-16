package pl.app.property.organization.application.domain.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import pl.app.common.model.BaseAuditEntity;

import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_organization")
public class OrganizationEntity extends BaseAuditEntity<UUID> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "organization_id", nullable = false)
    private UUID organizationId;

    @Column(nullable = false)
    private String name;

    @Override
    public UUID getId() {
        return this.organizationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        OrganizationEntity that = (OrganizationEntity) o;
        return organizationId != null && Objects.equals(organizationId, that.organizationId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
