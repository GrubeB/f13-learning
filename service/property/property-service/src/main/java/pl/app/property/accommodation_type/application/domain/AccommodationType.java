package pl.app.property.accommodation_type.application.domain;


import jakarta.persistence.*;
import lombok.Getter;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.BaseJpaAuditDomainAggregateRoot;
import pl.app.common.ddd.annotation.AggregateRootAnnotation;
import pl.app.common.ddd.event.DomainEventPublisher;
import pl.app.property.accommodation_type.application.domain.event.AccommodationCreatedEvent;
import pl.app.property.accommodation_type.application.domain.event.AccommodationRemovedEvent;
import pl.app.property.accommodation_type.application.domain.event.AccommodationTypeCreatedEvent;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;


@AggregateRootAnnotation
@Entity
@Getter
@Table(name = "t_accommodation_type")
public class AccommodationType extends BaseJpaAuditDomainAggregateRoot<AccommodationType> {
    @OneToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            mappedBy = "accommodationType",
            orphanRemoval = true)
    private final Set<Accommodation> accommodations = new LinkedHashSet<>();
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "aggregateId", column = @Column(name = "property_id", nullable = false))
    })
    private AggregateId property;

    @SuppressWarnings("unused")
    protected AccommodationType() {
        super();
    }

    public AccommodationType(DomainEventPublisher eventPublisher, AggregateId propertyId) {
        super(eventPublisher);
        this.property = propertyId;
        eventPublisher.publish(new AccommodationTypeCreatedEvent(this.property.getId(), this.aggregateId.getId()));
    }

    public void addAccommodation(Accommodation accommodation) {
        if (!new AccommodationTypeSpecification.UniqueAccommodationName(this).isSatisfiedBy(accommodation)) {
            throw new AccommodationTypeException.DuplicatedAccommodationNameException();
        }
        accommodations.add(accommodation);
        eventPublisher.publish(new AccommodationCreatedEvent(property.getId(), aggregateId.getId(), accommodation.getId()));
    }

    public void removeAccommodation(Accommodation accommodation) {
        if (accommodations.removeIf(acc -> acc.getAggregateId().equals(accommodation.getAggregateId()))) {
            eventPublisher.publish(new AccommodationRemovedEvent(property.getId(), aggregateId.getId(), accommodation.getId()));
        }
    }

    public void removeAccommodationById(AggregateId accommodation) {
        Accommodation accommodationToRemove = getAccommodationById(accommodation)
                .orElseThrow(() -> AccommodationTypeException.NotFoundAccommodationException.fromId(accommodation.getId()));
        this.removeAccommodation(accommodationToRemove);
    }

    private Optional<Accommodation> getAccommodationById(AggregateId accommodation) {
        return this.accommodations.stream()
                .filter(acc -> acc.getAggregateId().equals(accommodation))
                .findFirst();
    }
}
