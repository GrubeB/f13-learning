package pl.app.property.accommodation_type.adapter.out.persistence;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import pl.app.common.model.AbstractEntity;
import pl.app.property.accommodation_type_details.application.domain.model.AccommodationTypeDetailsEntity;
import pl.app.property.property.application.domain.model.PropertyEntity;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_accommodation_type")
public class AccommodationTypeEntity extends AbstractEntity<UUID> {
    @Id
    @Column(name = "accommodation_type_id", nullable = false)
    private UUID accommodationTypeId;

    @OneToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            mappedBy = "accommodationType",
            orphanRemoval = true)
    @Builder.Default
    private Set<AccommodationEntity> accommodations = new LinkedHashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private PropertyEntity property;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "accommodationType", orphanRemoval = true)
    private AccommodationTypeDetailsEntity accommodationTypeDetails;

    public void setAccommodationTypeDetails(AccommodationTypeDetailsEntity accommodationTypeDetails) {
        if (Objects.nonNull(accommodationTypeDetails)) {
            accommodationTypeDetails.setAccommodationType(this);
            this.accommodationTypeDetails = accommodationTypeDetails;
        } else {
            this.accommodationTypeDetails = null;
        }
    }

    public void setAccommodations(Set<AccommodationEntity> accommodations) {
        if (Objects.nonNull(accommodations) && !accommodations.isEmpty()) {
            accommodations.forEach(ch -> ch.setAccommodationType(this));
            this.accommodations.addAll(accommodations);
        } else {
            this.accommodations.clear();
        }
    }

    @Override
    public UUID getId() {
        return accommodationTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AccommodationTypeEntity that = (AccommodationTypeEntity) o;
        return accommodationTypeId != null && Objects.equals(accommodationTypeId, that.accommodationTypeId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
