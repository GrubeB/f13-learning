package pl.app.property.accommodation_availability.application.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.BaseJpaAuditDomainAggregateRoot;
import pl.app.common.ddd.annotation.AggregateRootAnnotation;
import pl.app.common.ddd.annotation.DataTransferObjectAnnotation;
import pl.app.common.ddd.shared.DateRange;
import pl.app.property.accommodation_availability.application.domain.AccommodationAssignmentPolicy;
import pl.app.property.accommodation_availability.application.domain.AccommodationAvailabilityException;
import pl.app.property.accommodation_availability.application.domain.AccommodationTypeAvailabilityPolicy;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@AggregateRootAnnotation
@Entity
@Getter
@Table(name = "t_accommodation_type_availability")
public class AccommodationTypeAvailability extends BaseJpaAuditDomainAggregateRoot<AccommodationTypeAvailability> {
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "aggregateId", column = @Column(name = "property_id", nullable = false))
    })
    private AggregateId propertyId;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "aggregateId", column = @Column(name = "accommodation_type_id", nullable = false))
    })
    private AggregateId accommodationTypeId;

    @OneToMany(mappedBy = "accommodationTypeAvailability", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<AccommodationAvailability> accommodationAvailabilities = new LinkedHashSet<>();
    @OneToMany(mappedBy = "accommodationTypeAvailability", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<AccommodationTypeReservation> typeReservations = new LinkedHashSet<>();

    @Transient
    private AccommodationAssignmentPolicy assignmentPolicy;
    @Transient
    private AccommodationTypeAvailabilityPolicy typeAvailabilityPolicy;

    @SuppressWarnings("unused")
    protected AccommodationTypeAvailability() {
        super();
    }

    public AccommodationTypeAvailability(AggregateId propertyId, AggregateId accommodationTypeId) {
        super();
        this.propertyId = propertyId;
        this.accommodationTypeId = accommodationTypeId;
    }

    // AVAILABILITY
    public void verifyAccommodationTypeAvailability(DateRange<LocalDate> dateRange, Integer numberOdAccommodations) {
        if (!isAccommodationTypeAvailable(dateRange, numberOdAccommodations)) {
            throw new AccommodationAvailabilityException.AccommodationNoAvailableException();
        }
    }

    public boolean isAccommodationTypeAvailable(DateRange<LocalDate> dateRange, Integer numberOdAccommodations) {
        return typeAvailabilityPolicy.isAccommodationTypeAvailable(this, numberOdAccommodations, dateRange);
    }

    // RESERVATION
    public AccommodationTypeReservation createTypeReservation(DateRange<LocalDate> dateRange) {
        verifyAccommodationTypeAvailability(dateRange, 1);
        AccommodationTypeReservation newAccommodationTypeReservation = new AccommodationTypeReservation(dateRange, this);
        typeReservations.add(newAccommodationTypeReservation);
        return newAccommodationTypeReservation;
    }

    public void removeTypeReservation(UUID typeReservationId) {
        AccommodationTypeReservation typeReservation = getTypeReservationById(typeReservationId);
        removeTypeReservation(typeReservation);
    }

    private void removeTypeReservation(AccommodationTypeReservation typeReservation) {
        typeReservation.removeAllReservations();
        typeReservations.remove(typeReservation);
    }

    // ASSIGN TYPE RESERVATION
    public void tryToAutoAssignTypeReservation(UUID typeReservationId) {
        tryToAutoAssignTypeReservation(getTypeReservationById(typeReservationId));
    }

    public void tryToAutoAssignTypeReservation(AccommodationTypeReservation typeReservation) {
        typeReservation.tryToAutoAssignTypeReservation(this, assignmentPolicy);
    }

    public void tryToManualAssignTypeReservations(UUID typeReservationId, List<ReservationRequest> newAccommodationReservations) {
        AccommodationTypeReservation typeReservation = getTypeReservationById(typeReservationId);
        typeReservation.tryToManualAssignTypeReservations(newAccommodationReservations.stream()
                .map(r -> new AccommodationTypeReservation.ReservationRequest(
                        getAccommodationById(r.accommodationId),
                        r.dateRange
                )).toList());
    }

    @DataTransferObjectAnnotation
    public record ReservationRequest(UUID accommodationId, DateRange<LocalDate> dateRange) {
    }

    // GETTERS
    public AccommodationAvailability getAccommodationById(UUID accommodationId) {
        return accommodationAvailabilities.stream()
                .filter(accommodation -> Objects.equals(accommodation.getId(), accommodationId))
                .findFirst().orElseThrow(() -> AccommodationAvailabilityException.NotFoundAccommodationException.fromId(accommodationId));
    }

    public AccommodationAvailability getAccommodationByRestrictionId(UUID restrictionId) {
        return accommodationAvailabilities.stream()
                .filter(accommodation -> accommodation.containsRestrictionById(restrictionId))
                .findFirst().orElseThrow(AccommodationAvailabilityException.NotFoundAccommodationException::new);
    }

    public List<AccommodationRestriction> getReservations() {
        return this.accommodationAvailabilities.stream()
                .map(AccommodationAvailability::getRestrictions)
                .flatMap(Set::stream)
                .collect(Collectors.toList());
    }

    public AccommodationRestriction getRestrictionById(UUID reservationId) {
        return getReservations().stream()
                .filter(reservation -> reservationId.equals(reservation.getId()))
                .findAny().orElseThrow(() -> AccommodationAvailabilityException.NotFoundAccommodationTypeReservationException.fromId(reservationId));
    }

    public AccommodationTypeReservation getTypeReservationById(UUID typeReservationId) {
        return this.typeReservations.stream()
                .filter(reservation -> typeReservationId.equals(reservation.getId()))
                .findAny().orElseThrow(() -> AccommodationAvailabilityException.NotFoundAccommodationTypeReservationException.fromId(typeReservationId));
    }

    public List<AccommodationRestriction> getAssignedReservationsInRange(DateRange<LocalDate> dateRange) {
        return getReservations().stream()
                .filter(reservation -> reservation.isRestrictionCollideWith(dateRange))
                .collect(Collectors.toList());
    }

    public List<AccommodationTypeReservation> getNoAssignedReservations() {
        return this.typeReservations.stream()
                .filter(Predicate.not(AccommodationTypeReservation::isAssigned))
                .collect(Collectors.toList());
    }

    public List<AccommodationTypeReservation> getNoAssignedReservationsInRange(DateRange<LocalDate> dateRange) {
        return this.getNoAssignedReservations().stream()
                .filter(typeReservation -> typeReservation.isReservationCollideWith(dateRange))
                .collect(Collectors.toList());
    }

    public void setPolicies(AccommodationAssignmentPolicy assignmentPolicy, AccommodationTypeAvailabilityPolicy typeAvailabilityPolicy) {
        this.assignmentPolicy = assignmentPolicy;
        this.typeAvailabilityPolicy = typeAvailabilityPolicy;
    }

}
