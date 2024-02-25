package pl.app.property.accommodation_availability.application.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.BaseJpaAuditDomainAggregateRoot;
import pl.app.common.ddd.annotation.AggregateRootAnnotation;
import pl.app.common.ddd.shared.DateRange;

import java.time.LocalDate;
import java.util.*;

@AggregateRootAnnotation
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@Table(name = "t_accommodation_availability")
public class AccommodationAvailability extends BaseJpaAuditDomainAggregateRoot<AccommodationTypeAvailability> {
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "aggregateId", column = @Column(name = "accommodation_id", nullable = false))
    })
    private AggregateId accommodation;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "accommodationAvailability", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<AccommodationRestriction> restrictions = new LinkedHashSet<>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "accommodation_type_availability",nullable = false)
    private AccommodationTypeAvailability accommodationTypeAvailability;

    @SuppressWarnings("unused")
    protected AccommodationAvailability() {
        super();
    }

    public AccommodationAvailability(AggregateId accommodation, AccommodationTypeAvailability accommodationTypeAvailability) {
        super();
        this.accommodation = accommodation;
        this.accommodationTypeAvailability = accommodationTypeAvailability;
    }


    // AVAILABILITY
    public void verifyAvailability(DateRange<LocalDate> dateRange) {
        if (!isAvailable(dateRange)) {
            throw AccommodationAvailabilityException.AccommodationNoAvailableException.fromId(getId());
        }
    }

    public boolean isAvailable(DateRange<LocalDate> dateRange) {
        return restrictions.stream()
                .noneMatch(reservation -> reservation.isRestrictionCollideWith(dateRange));
    }

    // RESTRICTION
    public AccommodationRestriction createRestriction(DateRange<LocalDate> dateRange, AccommodationRestrictionStatus status) {
        verifyAvailability(dateRange);
        AccommodationRestriction newRestriction = new AccommodationRestriction(dateRange, status, this);
        restrictions.add(newRestriction);
        return newRestriction;
    }

    public void removeRestriction(UUID restrictionId) {
        AccommodationRestriction accommodationRestriction = this.getRestrictionById(restrictionId)
                .orElseThrow(() -> AccommodationAvailabilityException.NotFoundAccommodationRestrictionException.fromId(restrictionId));
        restrictions.remove(accommodationRestriction);
    }

    public boolean containsRestrictionById(UUID restrictionId) {
        return restrictions.stream()
                .anyMatch(restriction -> Objects.equals(restriction.getId(), restrictionId));
    }

    // GETTERS
    public Optional<AccommodationRestriction> getRestrictionById(UUID restrictionId) {
        return this.restrictions.stream()
                .filter(restriction -> Objects.equals(restriction.getId(), restrictionId))
                .findAny();
    }
}