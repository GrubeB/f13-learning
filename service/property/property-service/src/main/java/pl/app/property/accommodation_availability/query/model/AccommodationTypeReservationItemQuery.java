package pl.app.property.accommodation_availability.query.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import pl.app.common.model.BaseAuditEntity;
import pl.app.property.accommodation_type.query.model.AccommodationTypeQuery;

import java.util.UUID;

@Entity
@Immutable
@Getter
@Setter
@Table(name = "t_accommodation_type_reservation_item")
public class AccommodationTypeReservationItemQuery extends BaseAuditEntity<AccommodationTypeQuery, UUID> {
    @Id
    private UUID id;

    @OneToOne
    @JoinColumn(name = "accommodation_availability")
    private AccommodationAvailabilityQuery accommodationAvailability;

    @OneToOne
    @JoinColumn(name = "accommodation_restriction")
    private AccommodationRestrictionQuery restriction;

    @ManyToOne(optional = false)
    @JoinColumn(name = "accommodation_type_reservation", nullable = false)
    private AccommodationTypeReservationQuery accommodationTypeReservation;
}