package pl.app.property.accommodation_availability.application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.app.ddd.annotation.ValueObjectAnnotation;

@ValueObjectAnnotation
@Getter
@AllArgsConstructor
public class AccommodationTypeReservationItem {
    private final Accommodation accommodation;
    private final AccommodationRestriction restriction;
}