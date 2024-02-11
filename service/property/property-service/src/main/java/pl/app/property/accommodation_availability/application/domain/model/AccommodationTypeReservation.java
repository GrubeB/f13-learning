package pl.app.property.accommodation_availability.application.domain.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.app.common.util.DateUtils;
import pl.app.common.ddd.BaseEntity;
import pl.app.common.ddd.annotation.DataTransferObjectAnnotation;
import pl.app.common.ddd.annotation.EntityAnnotation;
import pl.app.common.ddd.shared.DateRange;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@EntityAnnotation
@Getter
@AllArgsConstructor
public class AccommodationTypeReservation extends BaseEntity {
    private DateRange<LocalDate> dateRange;
    private TypeReservationAssignedStatus assignedStatus;
    private List<AccommodationTypeReservationItem> reservationItems;

    public AccommodationTypeReservation(DateRange<LocalDate> dateRange) {
        super();
        this.dateRange = dateRange;
        this.assignedStatus = TypeReservationAssignedStatus.NO_ASSIGNED;
        this.reservationItems = new ArrayList<>();
    }

    public AccommodationTypeReservation(UUID entityId,
                                        DateRange<LocalDate> dateRange,
                                        TypeReservationAssignedStatus assignedStatus,
                                        List<AccommodationTypeReservationItem> reservationItems) {
        super(entityId);
        this.dateRange = dateRange;
        this.assignedStatus = assignedStatus;
        this.reservationItems = reservationItems;
    }

    // RESERVATION
    public void createReservation(Accommodation accommodation, DateRange<LocalDate> dateRange) {
        verifyIfDateRangeCollideWithExistingReservations(dateRange);
        accommodation.verifyAvailability(dateRange);
        AccommodationRestriction newRestriction = accommodation.createRestriction(dateRange, AccommodationRestrictionStatus.RESERVED);
        addReservation(accommodation, newRestriction);
    }

    private void addReservation(Accommodation accommodation, AccommodationRestriction restriction) {
        reservationItems.add(new AccommodationTypeReservationItem( accommodation, restriction));

    }

    public void removeAllReservations() {
        setAssignedStatus(TypeReservationAssignedStatus.NO_ASSIGNED);
        reservationItems.forEach(reservationItem -> {
            UUID restrictionId = reservationItem.getRestriction().getId();
            reservationItem.getAccommodation().removeRestriction(restrictionId);
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
                typeAvailability.getAccommodationById(possibleReservation.accommodationId()),
                possibleReservation.dateRange()
        ));
        setAssignedStatus(TypeReservationAssignedStatus.AUTO_ASSIGNED);
    }

    public void tryToManualAssignTypeReservations(List<ReservationRequest> newReservations) {
        if (isAssigned()) {
            removeAllReservations();
        }
        newReservations.forEach(newReservation -> createReservation(
                newReservation.accommodation(),
                newReservation.dateRange()
        ));
        setAssignedStatus(TypeReservationAssignedStatus.MANUAL_ASSIGNED);
    }

    @DataTransferObjectAnnotation
    public record ReservationRequest(Accommodation accommodation, DateRange<LocalDate> dateRange) {
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
