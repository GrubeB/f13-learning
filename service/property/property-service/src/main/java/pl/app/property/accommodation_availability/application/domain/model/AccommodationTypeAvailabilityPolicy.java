package pl.app.property.accommodation_availability.application.domain.model;

import pl.app.ddd.annotation.PolicyAnnotation;
import pl.app.ddd.shared.DateRange;
import pl.app.property.accommodation_availability.application.domain.model.AccommodationTypeAvailability;

import java.time.LocalDate;

// checks if there will be accommodations available for a given period of time
@PolicyAnnotation
public interface AccommodationTypeAvailabilityPolicy {
    boolean isAccommodationTypeAvailable(
            AccommodationTypeAvailability availability,
            Integer numberOdAccommodations,
            DateRange<LocalDate> dateRange);
}