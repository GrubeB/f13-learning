package pl.app.property.accommodation_availability.application.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.BaseJpaAuditDomainAggregateRoot;
import pl.app.common.ddd.annotation.AggregateRootAnnotation;
import pl.app.common.ddd.annotation.DataTransferObjectAnnotation;
import pl.app.common.ddd.shared.DateRange;
import pl.app.common.util.DateUtils;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@AggregateRootAnnotation
@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "t_accommodation_type_reservation")
public class AccommodationTypeReservation extends BaseJpaAuditDomainAggregateRoot<AccommodationTypeReservation> {
    @AttributeOverrides({
            @AttributeOverride(name = "fromDate", column = @Column(name = "from_date", nullable = false)),
            @AttributeOverride(name = "toDate", column = @Column(name = "to_date", nullable = false)),
    })
    private DateRange<LocalDate> dateRange;
    @Enumerated(EnumType.STRING)
    @Column(name = "assigned_status", nullable = false)
    private TypeReservationAssignedStatus assignedStatus;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "accommodationTypeReservation", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<AccommodationTypeReservationItem> reservationItems = new LinkedHashSet<>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "accommodation_type_availability", nullable = false)
    private AccommodationTypeAvailability accommodationTypeAvailability;

    @SuppressWarnings("unused")
    protected AccommodationTypeReservation() {
        super();
    }

    public AccommodationTypeReservation(DateRange<LocalDate> dateRange, AccommodationTypeAvailability accommodationTypeAvailability) {
        super();
        this.dateRange = dateRange;
        this.assignedStatus = TypeReservationAssignedStatus.NO_ASSIGNED;
        this.accommodationTypeAvailability = accommodationTypeAvailability;
    }

    // RESERVATION
    public void createReservation(AccommodationAvailability accommodationAvailability, DateRange<LocalDate> dateRange) {
        verifyIfDateRangeCollideWithExistingReservations(dateRange);
        accommodationAvailability.verifyAvailability(dateRange);
        AccommodationRestriction newRestriction = accommodationAvailability.createRestriction(dateRange, AccommodationRestrictionStatus.RESERVED);
        addReservation(accommodationAvailability, newRestriction);
    }

    private void addReservation(AccommodationAvailability accommodationAvailability, AccommodationRestriction restriction) {
        reservationItems.add(new AccommodationTypeReservationItem(accommodationAvailability, restriction, this));

    }

    public void removeAllReservations() {
        setAssignedStatus(TypeReservationAssignedStatus.NO_ASSIGNED);
        reservationItems.forEach(reservationItem -> {
            UUID restrictionId = reservationItem.getRestriction().getId();
            reservationItem.getAccommodationAvailability().removeRestriction(restrictionId);
        });
        reservationItems.clear();
    }

    // ASSIGNATION
    public void tryToAutoAssignTypeReservation(AccommodationTypeAvailability typeAvailability, AccommodationAssignmentPolicy assignmentPolicy) {
        if (isAssigned()) {
            removeAllReservations();
        }
        List<AccommodationAssignmentPolicy.PossibleReservation> possibleReservations = assignmentPolicy
                .getPossibleAccommodationToReservation(typeAvailability, this);
        if (possibleReservations.isEmpty()) {
            return;
        }
        possibleReservations.forEach(possibleReservation -> createReservation(
                typeAvailability.getAccommodationByIdOrThrow(new AggregateId(possibleReservation.accommodationId())),
                possibleReservation.dateRange()
        ));
        setAssignedStatus(TypeReservationAssignedStatus.AUTO_ASSIGNED);
    }

    public void autoAssignTypeReservation(AccommodationTypeAvailability typeAvailability, AccommodationAssignmentPolicy assignmentPolicy) {
        if (isAssigned()) {
            removeAllReservations();
        }
        List<AccommodationAssignmentPolicy.PossibleReservation> possibleReservations = assignmentPolicy
                .getPossibleAccommodationToReservation(typeAvailability, this);
        if (possibleReservations.isEmpty()) {
            return;
        }
        possibleReservations.forEach(possibleReservation -> createReservation(
                typeAvailability.getAccommodationByIdOrThrow(new AggregateId(possibleReservation.accommodationId())),
                possibleReservation.dateRange()
        ));
        setAssignedStatus(TypeReservationAssignedStatus.AUTO_ASSIGNED);
    }

    public void manualAssignTypeReservations(List<ReservationRequest> newReservations) {
        if (isAssigned()) {
            removeAllReservations();
        }
        newReservations.forEach(newReservation -> createReservation(
                newReservation.accommodationAvailability(),
                newReservation.dateRange()
        ));
        setAssignedStatus(TypeReservationAssignedStatus.MANUAL_ASSIGNED);
    }

    @DataTransferObjectAnnotation
    public record ReservationRequest(AccommodationAvailability accommodationAvailability,
                                     DateRange<LocalDate> dateRange) {
    }

    // VERIFYING/VALIDATION
    public boolean isReservationCollideWith(DateRange<LocalDate> dateRange) {
        return DateUtils.isDateRangesCollide(dateRange.getFromDate(), dateRange.getToDate(), dateRange.getFromDate(), dateRange.getToDate());
    }

    private void verifyIfDateRangeCollideWithExistingReservations(DateRange<LocalDate> dateRange) {
        if (!isDateRangeCollideWithExistingReservations(dateRange)) {
            throw new AccommodationAvailabilityException.AccommodationReservationValidationException();
        }
    }

    private boolean isDateRangeCollideWithExistingReservations(DateRange<LocalDate> dateRange) {
        if (isReservationCollideWith(dateRange)) { // data range must be in type reservation range
            return true;
        }
        return getRestrictions().stream()
                .noneMatch(restriction -> restriction.isRestrictionCollideWith(dateRange));
    }

    // STATUS
    public void setAssignedStatus(TypeReservationAssignedStatus newAssignedStatus) {
        assignedStatus = newAssignedStatus;
    }

    private void verifyIfIsNoAssigned() {
        if (isAssigned()) {
            throw new AccommodationAvailabilityException.AccommodationReservationValidationException();
        }
    }

    public Boolean isAssigned() {
        return !TypeReservationAssignedStatus.NO_ASSIGNED.equals(assignedStatus);
    }

    // GETTERS
    private List<AccommodationRestriction> getRestrictions() {
        return reservationItems.stream()
                .map(AccommodationTypeReservationItem::getRestriction)
                .collect(Collectors.toList());
    }
}
