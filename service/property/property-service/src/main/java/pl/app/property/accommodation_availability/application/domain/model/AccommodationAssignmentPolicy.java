package pl.app.property.accommodation_availability.application.domain.model;

import pl.app.ddd.annotation.DataTransferObjectAnnotation;
import pl.app.ddd.annotation.PolicyAnnotation;
import pl.app.ddd.shared.DateRange;
import pl.app.property.accommodation_availability.application.domain.model.AccommodationTypeAvailability;
import pl.app.property.accommodation_availability.application.domain.model.AccommodationTypeReservation;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

// try to return possible accommodation to reservation
@PolicyAnnotation
public interface AccommodationAssignmentPolicy {
    List<PossibleReservation> getPossibleAccommodationToReservation(
            AccommodationTypeAvailability typeAvailability,
            AccommodationTypeReservation typeReservation);

    @DataTransferObjectAnnotation
    record PossibleReservation(DateRange<LocalDate> dateRange, UUID accommodationId) {
    }
}
