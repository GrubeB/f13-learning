package pl.app.property.accommodation_availability.application.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.BaseJpaAuditDomainAggregateRoot;
import pl.app.common.ddd.annotation.AggregateRootAnnotation;
import pl.app.common.ddd.annotation.DataTransferObjectAnnotation;
import pl.app.common.ddd.shared.DateRange;

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
            @AttributeOverride(name = "aggregateId", column = @Column(name = "property", nullable = false))
    })
    private AggregateId property;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "aggregateId", column = @Column(name = "accommodation_type", nullable = false))
    })
    private AggregateId accommodationType;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "accommodationTypeAvailability", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<AccommodationAvailability> accommodationAvailabilities = new LinkedHashSet<>();
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "accommodationTypeAvailability", cascade = CascadeType.ALL, orphanRemoval = true)
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

    public AccommodationTypeAvailability(AggregateId property, AggregateId accommodationType) {
        super();
        this.property = property;
        this.accommodationType = accommodationType;
    }

    // ACCOMMODATIONS
    public void addAccommodationAvailability(AggregateId accommodationId) {
        Optional<AccommodationAvailability> accommodationById = getAccommodationById(accommodationId);
        if (accommodationById.isPresent()) {
            return;
        }
        AccommodationAvailability newAccommodationAvailability = new AccommodationAvailability(accommodationId, this);
        this.accommodationAvailabilities.add(newAccommodationAvailability);
    }

    public void removeAccommodationAvailability(AggregateId accommodationId) {
        getAccommodationById(accommodationId).ifPresent(accommodationAvailability ->
                this.accommodationAvailabilities.remove(accommodationAvailability));
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

    public void removeTypeReservation(AggregateId typeReservationId) {
        AccommodationTypeReservation typeReservation = getTypeReservationById(typeReservationId);
        removeTypeReservation(typeReservation);
    }

    private void removeTypeReservation(AccommodationTypeReservation typeReservation) {
        typeReservation.removeAllReservations();
        typeReservations.remove(typeReservation);
    }

    // ASSIGN TYPE RESERVATION
    public void autoAssignTypeReservation(AggregateId typeReservationId) {
        autoAssignTypeReservation(getTypeReservationById(typeReservationId));
    }

    public void autoAssignTypeReservation(AccommodationTypeReservation typeReservation) {
        typeReservation.autoAssignTypeReservation(this, assignmentPolicy);
    }

    public void tryToManualAssignTypeReservations(AggregateId typeReservationId, List<ReservationRequest> newAccommodationReservations) {
        AccommodationTypeReservation typeReservation = getTypeReservationById(typeReservationId);
        typeReservation.manualAssignTypeReservations(newAccommodationReservations.stream()
                .map(r -> new AccommodationTypeReservation.ReservationRequest(
                        getAccommodationByIdOrThrow(r.accommodationId),
                        r.dateRange
                )).toList());
    }

    @DataTransferObjectAnnotation
    public record ReservationRequest(AggregateId accommodationId, DateRange<LocalDate> dateRange) {
    }

    // GETTERS
    public AccommodationAvailability getAccommodationByIdOrThrow(AggregateId accommodationId) {
        return getAccommodationById(accommodationId)
                .orElseThrow(() -> AccommodationAvailabilityException.NotFoundAccommodationException.fromId(accommodationId.getId()));
    }

    public Optional<AccommodationAvailability> getAccommodationById(AggregateId accommodationId) {
        return accommodationAvailabilities.stream()
                .filter(accommodation -> Objects.equals(accommodation.getAccommodation(), accommodationId))
                .findFirst();
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

    public AccommodationTypeReservation getTypeReservationById(AggregateId typeReservationId) {
        return this.typeReservations.stream()
                .filter(reservation -> Objects.equals(typeReservationId, reservation.getAggregateId()))
                .findAny().orElseThrow(() -> AccommodationAvailabilityException.NotFoundAccommodationTypeReservationException.fromId(typeReservationId.getId()));
    }

    public Set<AccommodationRestriction> getAssignedReservationsInRange(DateRange<LocalDate> dateRange) {
        return getReservations().stream()
                .filter(reservation -> reservation.isRestrictionCollideWith(dateRange))
                .collect(Collectors.toSet());
    }

    public Set<AccommodationTypeReservation> getNoAssignedReservations() {
        return this.typeReservations.stream()
                .filter(Predicate.not(AccommodationTypeReservation::isAssigned))
                .collect(Collectors.toSet());
    }

    public Set<AccommodationTypeReservation> getNoAssignedReservationsInRange(DateRange<LocalDate> dateRange) {
        return this.getNoAssignedReservations().stream()
                .filter(typeReservation -> typeReservation.isReservationCollideWith(dateRange))
                .collect(Collectors.toSet());
    }

    public void setPolicies(AccommodationAssignmentPolicy assignmentPolicy, AccommodationTypeAvailabilityPolicy typeAvailabilityPolicy) {
        this.assignmentPolicy = assignmentPolicy;
        this.typeAvailabilityPolicy = typeAvailabilityPolicy;
    }

}
