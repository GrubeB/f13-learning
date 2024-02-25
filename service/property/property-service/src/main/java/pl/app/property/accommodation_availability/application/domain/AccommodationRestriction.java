package pl.app.property.accommodation_availability.application.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.app.common.ddd.BaseJpaAuditDomainEntity;
import pl.app.common.ddd.annotation.AggregateRootAnnotation;
import pl.app.common.ddd.annotation.EntityAnnotation;
import pl.app.common.ddd.shared.DateRange;
import pl.app.common.util.DateUtils;

import java.time.LocalDate;
import java.util.Objects;


@EntityAnnotation
@AggregateRootAnnotation
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@Table(name = "t_accommodation_restriction")
public class AccommodationRestriction extends BaseJpaAuditDomainEntity<AccommodationRestriction> {
    @Enumerated(EnumType.STRING)
    @Column(name = "restriction_status", nullable = false)
    private AccommodationRestrictionStatus status;
    @AttributeOverrides({
            @AttributeOverride(name = "fromDate", column = @Column(name = "from_date", nullable = false)),
            @AttributeOverride(name = "toDate", column = @Column(name = "to_date", nullable = false)),
    })
    private DateRange<LocalDate> dateRange;

    @ManyToOne(optional = false)
    @JoinColumn(name = "accommodation_availability", nullable = false)
    private AccommodationAvailability accommodationAvailability;

    @SuppressWarnings("unused")
    protected AccommodationRestriction() {
        super();
    }

    public AccommodationRestriction(DateRange<LocalDate> dateRange, AccommodationRestrictionStatus status, AccommodationAvailability accommodationAvailability) {
        this.status = status;
        this.dateRange = dateRange;
        this.accommodationAvailability = accommodationAvailability;
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
