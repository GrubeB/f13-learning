package pl.app.property.accommodation_availability.adapter.out.persistence;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import pl.app.common.model.BaseAuditEntity;
import pl.app.property.accommodation_availability.application.domain.model.TypeReservationAssignedStatus;

import java.time.LocalDate;
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
@Table(name = "t_accommodation_type_reservation")
public class AccommodationTypeReservationEntity extends BaseAuditEntity<UUID> {
    @Id
    @Column(name = "accommodation_type_reservation_id", nullable = false)
    private UUID accommodationTypeReservationId;

    @Enumerated(EnumType.STRING)
    private TypeReservationAssignedStatus status;

    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "t_accommodation_type_reservation_item",
            joinColumns = @JoinColumn(name = "accommodation_type_reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "accommodation_restriction_id"))
    @ToString.Exclude
    @Builder.Default
    private Set<AccommodationRestrictionEntity> accommodationRestrictions = new LinkedHashSet<>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "accommodation_type_availability_id")
    @ToString.Exclude
    @JsonIgnore
    private AccommodationTypeAvailabilityEntity accommodationTypeAvailability;


    public void setAccommodationTypeReservationItems(Set<AccommodationRestrictionEntity> accommodationRestrictionEntities) {
        if (Objects.nonNull(accommodationRestrictionEntities) && !accommodationRestrictionEntities.isEmpty()) {
            this.accommodationRestrictions.addAll(accommodationRestrictionEntities);
        } else {
            this.accommodationRestrictions.clear();
        }
    }

    @Override
    public UUID getId() {
        return accommodationTypeReservationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AccommodationTypeReservationEntity that = (AccommodationTypeReservationEntity) o;
        return accommodationTypeReservationId != null && Objects.equals(accommodationTypeReservationId, that.accommodationTypeReservationId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
