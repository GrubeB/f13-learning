package pl.app.property.accommodation_availability.application.domain.model.policy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.ddd.shared.DateRange;
import pl.app.property.accommodation_availability.application.domain.model.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// without overbooking
// only one accommodation
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class DefaultAccommodationTypeAvailabilityPolicy implements AccommodationTypeAvailabilityPolicy {

    @Override
    public boolean isAccommodationTypeAvailable(
            AccommodationTypeAvailability accommodationTypeAvailability,
            Integer numberOdAccommodations,
            DateRange<LocalDate> dateRange) {
        final List<Accommodation> accommodations = accommodationTypeAvailability.getAccommodations();
        final List<AccommodationRestriction> assignedReservation = accommodationTypeAvailability.getAssignedReservationsInRange(dateRange);
        final List<AccommodationTypeReservation> noAssignedReservationTypes = accommodationTypeAvailability.getNoAssignedReservationsInRange(dateRange);

        Map<LocalDate, Integer> numberOfReservationOnSpecificDay = getNumberOfReservationOnSpecificDay(assignedReservation, noAssignedReservationTypes);
        if(!numberOfReservationOnSpecificDay.values().stream()
                .allMatch(numberOfReservations -> numberOfReservations + numberOdAccommodations <= accommodations.size())){
            return false;
        }
        return accommodations.stream()
                .anyMatch(accommodation -> accommodation.isAvailable(dateRange));
    }

    private Map<LocalDate, Integer> getNumberOfReservationOnSpecificDay(
            List<AccommodationRestriction> assignedReservation,
            List<AccommodationTypeReservation> noAssignedReservationTypes) {
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

