package pl.app.property.accommodation_availability.application.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.app.common.ddd.BaseJpaAuditDomainEntity;
import pl.app.common.ddd.annotation.EntityAnnotation;

@EntityAnnotation
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@Table(name = "t_accommodation_type_reservation_item")
public class AccommodationTypeReservationItem extends BaseJpaAuditDomainEntity<AccommodationTypeReservationItem> {
    @OneToOne
    @JoinColumn(name = "accommodation_availability")
    private AccommodationAvailability accommodationAvailability;
    @OneToOne
    @JoinColumn(name = "accommodation_restriction")
    private AccommodationRestriction restriction;

    @ManyToOne(optional = false)
    @JoinColumn(name = "accommodation_type_reservation",nullable = false)
    private AccommodationTypeReservation accommodationTypeReservation;

    @SuppressWarnings("unused")
    protected AccommodationTypeReservationItem() {
        super();
    }
}