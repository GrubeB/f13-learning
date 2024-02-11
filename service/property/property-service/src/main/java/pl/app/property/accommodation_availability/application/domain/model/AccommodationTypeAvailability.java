package pl.app.property.accommodation_availability.application.domain.model;

import lombok.Getter;
import pl.app.ddd.AggregateId;
import pl.app.ddd.BaseAggregateRoot;
import pl.app.ddd.annotation.AggregateRootAnnotation;
import pl.app.ddd.annotation.DataTransferObjectAnnotation;
import pl.app.ddd.event.DomainEventPublisher;
import pl.app.ddd.shared.DateRange;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@AggregateRootAnnotation
@Getter
public class AccommodationTypeAvailability extends BaseAggregateRoot {
    private AggregateId propertyId;
    private AggregateId accommodationTypeId;
    private List<Accommodation> accommodations;
    private List<AccommodationTypeReservation> typeReservations;

    private AccommodationAssignmentPolicy assignmentPolicy;
    private AccommodationTypeAvailabilityPolicy typeAvailabilityPolicy;
    public AccommodationTypeAvailability(DomainEventPublisher eventPublisher, AggregateId propertyId, AggregateId accommodationTypeId,
                                         AccommodationAssignmentPolicy assignmentPolicy, AccommodationTypeAvailabilityPolicy typeAvailabilityPolicy) {
        super(eventPublisher);
        this.propertyId = propertyId;
        this.accommodationTypeId = accommodationTypeId;
        this.accommodations = new ArrayList<>();
        this.typeReservations = new ArrayList<>();
        this.assignmentPolicy = assignmentPolicy;
        this.typeAvailabilityPolicy = typeAvailabilityPolicy;
    }

    public AccommodationTypeAvailability(AggregateId aggregateId,
                                         DomainEventPublisher eventPublisher,
                                         AggregateId propertyId,
                                         AggregateId accommodationTypeId,
                                         List<Accommodation> accommodations,
                                         List<AccommodationTypeReservation> typeReservations) {
        super(aggregateId, eventPublisher);
        this.propertyId = propertyId;
        this.accommodationTypeId = accommodationTypeId;
        this.accommodations = accommodations;
        this.typeReservations = typeReservations;
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
        AccommodationTypeReservation newAccommodationTypeReservation = new AccommodationTypeReservation(dateRange);
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
    public Accommodation getAccommodationById(UUID accommodationId) {
        return accommodations.stream()
                .filter(accommodation -> accommodation.getId().equals(accommodationId))
                .findFirst().orElseThrow(() -> AccommodationAvailabilityException.NotFoundAccommodationException.fromId(accommodationId));
    }

    public Accommodation getAccommodationByRestrictionId(UUID restrictionId) {
        return accommodations.stream()
                .filter(accommodation -> accommodation.containsRestrictionById(restrictionId))
                .findFirst().orElseThrow(AccommodationAvailabilityException.NotFoundAccommodationException::new);
    }

    public List<AccommodationRestriction> getReservations() {
        return this.accommodations.stream()
                .map(Accommodation::getRestrictions)
                .flatMap(List::stream)
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
