package pl.app.property.property.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import pl.app.common.model.AbstractEntity;
import pl.app.property.organization.model.OrganizationEntity;

import java.time.LocalTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_property")
public class PropertyEntity extends AbstractEntity<UUID> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "property_id", nullable = false)
    private UUID propertyId;
    @Column(name = "property_name", nullable = false)
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private PropertyType propertyType;
    @Column(name = "check_in_from_time")
    private LocalTime checkInFromTime;
    @Column(name = "check_in_to_time")
    private LocalTime checkInToTime;
    @Column(name = "check_out_from_time")
    private LocalTime checkOutFromTime;
    @Column(name = "check_out_to_time")
    private LocalTime checkOutToTime;

    @OneToOne(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JoinColumn(name = "property_details_id")
    private PropertyDetailsEntity propertyDetails;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organization_id", nullable = false)
    private OrganizationEntity organization;
    public void setPropertyDetails(PropertyDetailsEntity propertyDetails) {
        this.propertyDetails = null;
        if (propertyDetails != null) {
            propertyDetails.setProperty(this);
            this.propertyDetails = propertyDetails;
        }
    }

    @Override
    public UUID getId() {
        return propertyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PropertyEntity that = (PropertyEntity) o;
        return propertyId != null && Objects.equals(propertyId, that.propertyId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
