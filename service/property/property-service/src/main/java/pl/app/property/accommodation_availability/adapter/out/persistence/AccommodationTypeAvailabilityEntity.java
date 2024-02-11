package pl.app.property.accommodation_availability.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import pl.app.common.model.AbstractEntity;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import pl.app.property.accommodation_type.adapter.out.persistence.AccommodationTypeEntity;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_accommodation_type_availability")
public class AccommodationTypeAvailabilityEntity extends AbstractEntity<UUID> {
    @Id
    @Column(name = "accommodation_type_availability_id", nullable = false)
    private UUID accommodationTypeAvailabilityId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "accommodation_type_id", nullable = false)
    private AccommodationTypeEntity accommodationType;

    @OneToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            mappedBy = "accommodationTypeAvailability",
            orphanRemoval = true)
    @Builder.Default
    private Set<AccommodationRestrictionEntity> accommodationRestrictions = new LinkedHashSet<>();

    @OneToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            mappedBy = "accommodationTypeAvailability",
            orphanRemoval = true)
    @Builder.Default
    private Set<AccommodationTypeReservationEntity> accommodationTypeReservations = new LinkedHashSet<>();

    public void setAccommodationRestrictions(Set<AccommodationRestrictionEntity> accommodationRestrictions) {
        if (Objects.nonNull(accommodationRestrictions) && !accommodationRestrictions.isEmpty()) {
            accommodationRestrictions.forEach(e -> e.setAccommodationTypeAvailability(this));
            this.accommodationRestrictions.addAll(accommodationRestrictions);
        } else {
            this.accommodationRestrictions.clear();
        }
    }

    public void setAccommodationTypeReservations(Set<AccommodationTypeReservationEntity> accommodationTypeReservations) {
        if (Objects.nonNull(accommodationTypeReservations) && !accommodationTypeReservations.isEmpty()) {
            accommodationTypeReservations.forEach(e -> e.setAccommodationTypeAvailability(this));
            this.accommodationTypeReservations.addAll(accommodationTypeReservations);
        } else {
            this.accommodationTypeReservations.clear();
        }
    }

    @Override
    public UUID getId() {
        return accommodationTypeAvailabilityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AccommodationTypeAvailabilityEntity that = (AccommodationTypeAvailabilityEntity) o;
        return accommodationTypeAvailabilityId != null && Objects.equals(accommodationTypeAvailabilityId, that.accommodationTypeAvailabilityId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
