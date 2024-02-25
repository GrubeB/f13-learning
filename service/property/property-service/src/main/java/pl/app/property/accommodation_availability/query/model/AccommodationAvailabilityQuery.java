package pl.app.property.accommodation_availability.query.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Immutable;
import pl.app.common.ddd.AggregateId;
import pl.app.common.model.BaseAuditEntity;

import java.util.*;

@Entity
@Immutable
@Getter
@Setter
@Table(name = "t_accommodation_availability")
public class AccommodationAvailabilityQuery extends BaseAuditEntity<AccommodationAvailabilityQuery, UUID> {
    @Id
    private UUID id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "aggregateId", column = @Column(name = "accommodation_id", nullable = false))
    })
    private AggregateId accommodation;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "accommodationAvailability", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<AccommodationRestrictionQuery> restrictions = new LinkedHashSet<>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "accommodation_type_availability",nullable = false)
    private AccommodationTypeAvailabilityQuery accommodationTypeAvailability;
}