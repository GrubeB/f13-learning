package pl.app.property.accommodation_availability.application.domain.model.policy;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.ddd.shared.DateRange;
import pl.app.property.accommodation_availability.application.domain.AccommodationTypeAvailabilityPolicy;
import pl.app.property.accommodation_availability.application.domain.model.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

// without overbooking
// any number of accommodations
@RequiredArgsConstructor
@Transactional(readOnly = true)
class MultiAccommodationTypeAvailabilityPolicy implements AccommodationTypeAvailabilityPolicy {

    @Override
    public boolean isAccommodationTypeAvailable(
            AccommodationTypeAvailability accommodationTypeAvailability,
            Integer numberOdAccommodations,
            DateRange<LocalDate> dateRange) {
        final Set<AccommodationAvailability> accommodationAvailabilities = accommodationTypeAvailability.getAccommodationAvailabilities();
        final Set<AccommodationRestriction> assignedReservation = accommodationTypeAvailability.getAssignedReservationsInRange(dateRange);
        final Set<AccommodationTypeReservation> noAssignedReservationTypes = accommodationTypeAvailability.getNoAssignedReservationsInRange(dateRange);

        Map<LocalDate, Integer> numberOfReservationOnSpecificDay = getNumberOfReservationOnSpecificDay(assignedReservation, noAssignedReservationTypes);
        return numberOfReservationOnSpecificDay.values().stream()
                .allMatch(numberOfReservations -> numberOfReservations + numberOdAccommodations <= accommodationAvailabilities.size());
    }

    private Map<LocalDate, Integer> getNumberOfReservationOnSpecificDay(
            Set<AccommodationRestriction> assignedReservation,
            Set<AccommodationTypeReservation> noAssignedReservationTypes) {
        Map<LocalDate, Integer> numberOfReservationOnSpecificDay = new HashMap<>(); // date, numberOfReservations
        assignedReservation.forEach(reservation -> {
            LocalDate reservationStartDate = reservation.getDateRange().getFromDate();
            LocalDate reservationEndDate = reservation.getDateRange().getToDate();
            while (!reservationStartDate.isAfter(reservationEndDate)) {
                Integer integer = numberOfReservationOnSpecificDay.computeIfAbsent(reservationStartDate, k -> 0);
                numberOfReservationOnSpecificDay.put(reservationStartDate, integer + 1);
                reservationStartDate = reservationStartDate.plusDays(1);
            }
        });
        noAssignedReservationTypes.forEach(reservation -> {
            LocalDate reservationStartDate = reservation.getDateRange().getFromDate();
            LocalDate reservationEndDate = reservation.getDateRange().getToDate();
            while (!reservationStartDate.isAfter(reservationEndDate)) {
                Integer integer = numberOfReservationOnSpecificDay.computeIfAbsent(reservationStartDate, k -> 0);
                numberOfReservationOnSpecificDay.put(reservationStartDate, integer + 1);
                reservationStartDate = reservationStartDate.plusDays(1);
            }
        });
        return numberOfReservationOnSpecificDay;
    }
}

