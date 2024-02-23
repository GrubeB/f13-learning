package pl.app.property.accommodation_availability.application.domain;

import org.springframework.stereotype.Component;
import pl.app.common.ddd.annotation.PolicyAnnotation;
import pl.app.common.ddd.shared.DateRange;
import pl.app.property.accommodation_availability.application.domain.model.AccommodationTypeAvailability;

import java.time.LocalDate;

// checks if there will be accommodations available for a given period of time
@PolicyAnnotation
@Component
public interface AccommodationTypeAvailabilityPolicy {
    boolean isAccommodationTypeAvailable(
            AccommodationTypeAvailability availability,
            Integer numberOdAccommodations,
            DateRange<LocalDate> dateRange);
}