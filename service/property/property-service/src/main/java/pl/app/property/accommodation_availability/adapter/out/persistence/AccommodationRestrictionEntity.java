package pl.app.property.accommodation_availability.adapter.out.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import pl.app.common.model.AbstractEntity;
import pl.app.property.accommodation_availability.application.domain.model.AccommodationRestrictionStatus;
import pl.app.property.accommodation_type.adapter.out.persistence.AccommodationEntity;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_accommodation_restriction")
public class AccommodationRestrictionEntity extends AbstractEntity<UUID> {

    @Id
    @Column(name = "accommodation_restriction_id", nullable = false)
    private UUID accommodationRestrictionId;

    @Enumerated(value = EnumType.STRING)
    private AccommodationRestrictionStatus status;

    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "accommodation_id")
    private AccommodationEntity accommodation;

    @ManyToOne
    @JoinColumn(name = "accommodation_type_availability_id")
    @ToString.Exclude
    @JsonIgnore
    private AccommodationTypeAvailabilityEntity accommodationTypeAvailability;

    @Override
    public UUID getId() {
        return accommodationRestrictionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AccommodationRestrictionEntity that = (AccommodationRestrictionEntity) o;
        return accommodationRestrictionId != null && Objects.equals(accommodationRestrictionId, that.accommodationRestrictionId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
