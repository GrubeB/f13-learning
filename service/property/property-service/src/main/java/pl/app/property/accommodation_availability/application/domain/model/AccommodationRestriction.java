package pl.app.property.accommodation_availability.application.domain.model;

import lombok.Getter;
import pl.app.common.util.DateUtils;
import pl.app.common.ddd.BaseEntity;
import pl.app.common.ddd.annotation.EntityAnnotation;
import pl.app.common.ddd.shared.DateRange;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;


@EntityAnnotation
@Getter
public class AccommodationRestriction extends BaseEntity {
    private AccommodationRestrictionStatus status;
    private final DateRange<LocalDate> dateRange;

    public AccommodationRestriction(DateRange<LocalDate> dateRange, AccommodationRestrictionStatus status) {
        this.status = status;
        this.dateRange = dateRange;
    }

    public AccommodationRestriction(UUID entityId, AccommodationRestrictionStatus status, DateRange<LocalDate> dateRange) {
        super(entityId);
        this.status = status;
        this.dateRange = dateRange;
    }
    // STATUS
    public void changeStatusTo(AccommodationRestrictionStatus newStatus) {
        if (Objects.isNull(newStatus) || status.equals(newStatus)) {
            return;
        }
        switch (status) {
            case RESERVED -> {
                if (AccommodationRestrictionStatus.GUEST_IN.equals(newStatus)
                        || AccommodationRestrictionStatus.BLOCKED.equals(newStatus)) {
                    status = newStatus;
                } else {
                    throw new AccommodationAvailabilityException.AccommodationRestrictionValidationException();
                }
            }
            case GUEST_IN -> {
                throw new AccommodationAvailabilityException.AccommodationRestrictionValidationException();
            }
            case BLOCKED -> {
                if (AccommodationRestrictionStatus.RESERVED.equals(newStatus)) {
                    status = newStatus;
                } else {
                    throw new AccommodationAvailabilityException.AccommodationRestrictionValidationException();
                }
            }
        }
    }
    // VERIFYING
    public boolean isRestrictionCollideWith(DateRange<LocalDate> dateRange) {
        return DateUtils.isDateRangesCollide(this.dateRange.getFromDate(), this.dateRange.getToDate(), dateRange.getFromDate(), dateRange.getToDate());
    }
}
