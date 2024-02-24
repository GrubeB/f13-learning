package pl.app.property.property.application.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.app.common.model.BaseAuditEntity;
import pl.app.property.organization.application.domain.Organization;

import java.time.LocalTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_property")
public class Property extends BaseAuditEntity<Property, UUID> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;
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
    private PropertyDetails propertyDetails;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    public void setPropertyDetails(PropertyDetails propertyDetails) {
        this.propertyDetails = propertyDetails;
        propertyDetails.setProperty(this);
    }
}
