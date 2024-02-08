package pl.app.property.accommodation_availability.application.domain.model;

import lombok.Getter;
import pl.app.ddd.BaseEntity;
import pl.app.ddd.annotation.EntityAnnotation;
import pl.app.ddd.shared.DateRange;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@EntityAnnotation
@Getter
public class Accommodation extends BaseEntity {
    private final List<AccommodationRestriction> restrictions;
    public Accommodation(UUID accommodationId) {
        super(accommodationId);
        restrictions = new ArrayList<>();
    }

    public Accommodation(UUID entityId, List<AccommodationRestriction> restrictions) {
        super(entityId);
        this.restrictions = restrictions;
    }

    // AVAILABILITY
    public void verifyAvailability(DateRange<LocalDate> dateRange) {
        if (!isAvailable(dateRange)) {
            throw AccommodationAvailabilityException.AccommodationNoAvailableException.fromId(entityId);
        }
    }
    public boolean isAvailable(DateRange<LocalDate> dateRange) {
        return restrictions.stream()
                .noneMatch(reservation -> reservation.isRestrictionCollideWith(dateRange));
    }

    // RESTRICTION
    public AccommodationRestriction createRestriction(DateRange<LocalDate> dateRange, AccommodationRestrictionStatus status) {
        verifyAvailability(dateRange);
        AccommodationRestriction newRestriction = new AccommodationRestriction(dateRange, status);
        restrictions.add(newRestriction);
        return newRestriction;
    }
    public void removeRestriction(UUID restrictionId) {
        AccommodationRestriction accommodationRestriction = this.getRestrictionById(restrictionId)
                .orElseThrow(() -> AccommodationAvailabilityException.NotFoundAccommodationRestrictionException.fromId(restrictionId));
        restrictions.remove(accommodationRestriction);
    }

    public boolean containsRestrictionById(UUID restrictionId) {
        return restrictions.stream()
                .anyMatch(restriction -> restriction.getId().equals(restrictionId));
    }

    // GETTERS
    public Optional<AccommodationRestriction> getRestrictionById(UUID restrictionId){
        return this.restrictions.stream()
                .filter(restriction -> restriction.getId().equals(restrictionId))
                .findAny();
    }
}