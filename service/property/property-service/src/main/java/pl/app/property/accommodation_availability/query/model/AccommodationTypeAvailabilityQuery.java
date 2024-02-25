package pl.app.property.accommodation_availability.query.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import pl.app.common.ddd.AggregateId;
import pl.app.common.model.BaseAuditEntity;

import java.util.*;


@Entity
@Immutable
@Getter
@Setter
@Table(name = "t_accommodation_type_availability")
public class AccommodationTypeAvailabilityQuery  extends BaseAuditEntity<AccommodationTypeAvailabilityQuery, UUID> {
    @Id
    private UUID id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "aggregateId", column = @Column(name = "property", nullable = false))
    })
    private AggregateId property;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "aggregateId", column = @Column(name = "accommodation_type", nullable = false))
    })
    private AggregateId accommodationType;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "accommodationTypeAvailability")
    private Set<AccommodationAvailabilityQuery> accommodationAvailabilities = new LinkedHashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "accommodationTypeAvailability")
    private Set<AccommodationTypeReservationQuery> typeReservations = new LinkedHashSet<>();
}
