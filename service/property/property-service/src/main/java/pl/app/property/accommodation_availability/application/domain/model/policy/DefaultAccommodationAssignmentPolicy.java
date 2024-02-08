package pl.app.property.accommodation_availability.application.domain.model.policy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.ddd.shared.DateRange;
import pl.app.property.accommodation_availability.application.domain.model.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

// try to reserve only one accommodation
// policy may reserve 1 reservation
@Service
@RequiredArgsConstructor
@Transactional
class DefaultAccommodationAssignmentPolicy implements
        AccommodationAssignmentPolicy {

    @Override
    public List<PossibleReservation> getPossibleAccommodationToReservation(
            AccommodationTypeAvailability availability,
            AccommodationTypeReservation typeReservation) {
        final List<Accommodation> accommodations = availability.getAccommodations();
        final DateRange<LocalDate> dateRange = typeReservation.getDateRange();

        Optional<Accommodation> possibleAccommodation = accommodations.stream()
                .filter(accommodation -> accommodation.isAvailable(dateRange))
                .findAny();
        return possibleAccommodation
                .map(accommodation -> List.of(new PossibleReservation(dateRange, accommodation.getId())))
                .orElse(Collections.emptyList());
    }
}
