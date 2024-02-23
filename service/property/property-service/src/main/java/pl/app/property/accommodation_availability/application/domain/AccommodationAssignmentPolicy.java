package pl.app.property.accommodation_availability.application.domain;

import org.springframework.stereotype.Component;
import pl.app.common.ddd.annotation.DataTransferObjectAnnotation;
import pl.app.common.ddd.annotation.PolicyAnnotation;
import pl.app.common.ddd.shared.DateRange;
import pl.app.property.accommodation_availability.application.domain.model.AccommodationTypeAvailability;
import pl.app.property.accommodation_availability.application.domain.model.AccommodationTypeReservation;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

// try to return possible accommodation to reservation
@PolicyAnnotation
@Component
public interface AccommodationAssignmentPolicy {
    List<PossibleReservation> getPossibleAccommodationToReservation(
            AccommodationTypeAvailability typeAvailability,
            AccommodationTypeReservation typeReservation);

    @DataTransferObjectAnnotation
    record PossibleReservation(DateRange<LocalDate> dateRange, UUID accommodationId) {
    }
}
