package pl.app.property.accommodation_availability.application.domain.model;


import org.springframework.stereotype.Component;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.annotation.FactoryAnnotation;
import pl.app.common.ddd.event.DomainEventPublisher;

@FactoryAnnotation
@Component
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
