package pl.app.property.accommodation_availability.application.domain.model.policy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.ddd.shared.DateRange;
import pl.app.property.accommodation_availability.application.domain.AccommodationAssignmentPolicy;
import pl.app.property.accommodation_availability.application.domain.model.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
        final Set<AccommodationAvailability> accommodationAvailabilities = availability.getAccommodationAvailabilities();
        final DateRange<LocalDate> dateRange = typeReservation.getDateRange();

        Optional<AccommodationAvailability> possibleAccommodation = accommodationAvailabilities.stream()
                .filter(accommodation -> accommodation.isAvailable(dateRange))
                .findAny();
        return possibleAccommodation
                .map(accommodation -> List.of(new PossibleReservation(dateRange, accommodation.getId())))
                .orElse(Collections.emptyList());
    }
}
