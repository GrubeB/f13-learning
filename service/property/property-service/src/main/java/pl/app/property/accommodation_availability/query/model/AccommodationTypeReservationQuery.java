package pl.app.property.accommodation_availability.query.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Immutable;
import pl.app.common.ddd.shared.DateRange;
import pl.app.common.model.BaseAuditEntity;
import pl.app.property.accommodation_availability.application.domain.TypeReservationAssignedStatus;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Immutable
@Getter
@Setter
@Table(name = "t_accommodation_type_reservation")
public class AccommodationTypeReservationQuery extends BaseAuditEntity<AccommodationTypeReservationQuery, UUID> {
    @Id
    private UUID id;

    @AttributeOverrides({
            @AttributeOverride(name = "fromDate", column = @Column(name = "from_date", nullable = false)),
            @AttributeOverride(name = "toDate", column = @Column(name = "to_date", nullable = false)),
    })
    private DateRange<LocalDate> dateRange;
    @Enumerated(EnumType.STRING)
    @Column(name = "assigned_status", nullable = false)
    private TypeReservationAssignedStatus assignedStatus;

    @OneToMany(mappedBy = "accommodationTypeReservation")
    @ToString.Exclude
    private Set<AccommodationTypeReservationItemQuery> reservationItems= new LinkedHashSet<>();

    @ManyToOne
    @JoinColumn(name = "accommodation_type_availability")
    private AccommodationTypeAvailabilityQuery accommodationTypeAvailability;
}
