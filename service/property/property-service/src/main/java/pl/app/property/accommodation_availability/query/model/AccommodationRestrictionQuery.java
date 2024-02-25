package pl.app.property.accommodation_availability.query.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import pl.app.common.ddd.shared.DateRange;
import pl.app.common.model.BaseAuditEntity;
import pl.app.property.accommodation_availability.application.domain.AccommodationRestrictionStatus;

import java.time.LocalDate;
import java.util.UUID;


@Entity
@Immutable
@Getter
@Setter
@Table(name = "t_accommodation_restriction")
public class AccommodationRestrictionQuery extends BaseAuditEntity<AccommodationRestrictionQuery, UUID> {
    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "restriction_status", nullable = false)
    private AccommodationRestrictionStatus status;

    @AttributeOverrides({
            @AttributeOverride(name = "fromDate", column = @Column(name = "from_date", nullable = false)),
            @AttributeOverride(name = "toDate", column = @Column(name = "to_date", nullable = false)),
    })
    private DateRange<LocalDate> dateRange;

    @ManyToOne(optional = false)
    @JoinColumn(name = "accommodation_availability", nullable = false)
    private AccommodationAvailabilityQuery accommodationAvailability;
}
