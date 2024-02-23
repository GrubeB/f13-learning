package pl.app.property.property.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "t_property_details")
public class PropertyDetails extends BaseAuditEntity<PropertyDetails, UUID> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "property_details_id", nullable = false)
    private UUID id;
    private String email;
    private String phone;
    private String website;
    private String description;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id", nullable = false)
    private PropertyAddress propertyAddress;

    @OneToOne(mappedBy = "propertyDetails")
    @ToString.Exclude
    @JsonIgnore
    private Property property;

    public void setPropertyAddress(PropertyAddress propertyAddress) {
        this.propertyAddress = propertyAddress;
        propertyAddress.setPropertyDetails(this);
    }
}
