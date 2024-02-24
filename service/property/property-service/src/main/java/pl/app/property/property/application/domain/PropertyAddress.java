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
@Table(name = "t_property_address")
public class PropertyAddress extends BaseAuditEntity<PropertyAddress, UUID> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;
    private String address1;
    private String address2;
    private String city;
    private String country;
    private String region;
    @Column(name = "zip_code")
    private String zipCode;

    @OneToOne(mappedBy = "propertyAddress", orphanRemoval = true)
    @ToString.Exclude
    @JsonIgnore
    private PropertyDetails propertyDetails;
}
