package pl.app.property.accommodation_availability.application.domain.model;


import lombok.RequiredArgsConstructor;
import pl.app.ddd.AggregateId;
import pl.app.ddd.annotation.FactoryAnnotation;
import pl.app.ddd.event.DomainEventPublisher;

@FactoryAnnotation
public class AccommodationTypeAvailabilityFactory {
    private final DomainEventPublisher domainEventPublisher;
    private final AccommodationAssignmentPolicy accommodationAssignmentPolicy;
    private final AccommodationTypeAvailabilityPolicy accommodationTypeAvailabilityPolicy;

    public AccommodationTypeAvailabilityFactory(DomainEventPublisher domainEventPublisher,
                                                AccommodationAssignmentPolicy defaultAccommodationAssignmentPolicy,
                                                AccommodationTypeAvailabilityPolicy defaultAccommodationTypeAvailabilityPolicy) {
        this.domainEventPublisher = domainEventPublisher;
        this.accommodationAssignmentPolicy = defaultAccommodationAssignmentPolicy;
        this.accommodationTypeAvailabilityPolicy = defaultAccommodationTypeAvailabilityPolicy;
    }

    public AccommodationTypeAvailability createAccommodationTypeAvailabilityFactory(AggregateId propertyId, AggregateId accommodationTypeId) {
        return new AccommodationTypeAvailability(domainEventPublisher, propertyId, accommodationTypeId,
                accommodationAssignmentPolicy, accommodationTypeAvailabilityPolicy);
    }
}
