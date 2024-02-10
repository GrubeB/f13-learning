package pl.app.property.accommodation_type.application.domain;


import lombok.Getter;
import pl.app.ddd.AggregateId;
import pl.app.ddd.BaseAggregateRoot;
import pl.app.ddd.annotation.AggregateRootAnnotation;
import pl.app.ddd.event.DomainEventPublisher;
import pl.app.property.accommodation_type.application.domain.event.AccommodationTypeCreatedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AggregateRootAnnotation
@Getter
public class AccommodationType extends BaseAggregateRoot {
    private final List<Accommodation> accommodations;
    private final AggregateId propertyId;

    public AccommodationType(DomainEventPublisher eventPublisher, AggregateId propertyId) {
        super(eventPublisher);
        this.accommodations = new ArrayList<>();
        this.propertyId = propertyId;
        eventPublisher.publish(new AccommodationTypeCreatedEvent(this.propertyId.getId(), this.aggregateId.getId()));
    }

    public AccommodationType(AggregateId aggregateId, List<Accommodation> accommodations, AggregateId propertyId) {
        super(aggregateId);
        this.propertyId = propertyId;
        this.accommodations = accommodations;
    }

    public void addAccommodation(Accommodation accommodation) {
        if (!new AccommodationTypeSpecification.UniqueAccommodationName(this).isSatisfiedBy(accommodation)) {
            throw new AccommodationTypeException.DuplicatedAccommodationNameException();
        }
        accommodations.add(accommodation);
    }

    public void removeAccommodation(Accommodation accommodation) {
        accommodations.removeIf(acc -> acc.getId().equals(accommodation.getId()));
    }

    public void removeAccommodationById(UUID accommodationId) {
        Accommodation accommodationToRemove = getAccommodationById(accommodationId)
                .orElseThrow(() -> AccommodationTypeException.NotFoundAccommodationException.fromId(accommodationId));
        this.removeAccommodation(accommodationToRemove);
    }

    private Optional<Accommodation> getAccommodationById(UUID accommodationId) {
        return this.accommodations.stream()
                .filter(acc -> acc.getId().equals(accommodationId))
                .findFirst();
    }
}
