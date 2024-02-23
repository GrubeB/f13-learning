package pl.app.property.organization.application.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.app.common.model.BaseAuditEntity;

import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_organization")
public class Organization extends BaseAuditEntity<Organization, UUID> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "organization_id", nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String name;
}
