package pl.app.property.accommodation_availability.application.domain;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.annotation.FactoryAnnotation;
import pl.app.common.ddd.event.DomainEventPublisherFactory;
import pl.app.property.accommodation_availability.application.domain.model.AccommodationTypeAvailability;

@FactoryAnnotation
@Component
@AllArgsConstructor
public class AccommodationTypeAvailabilityFactory {
    private final DomainEventPublisherFactory domainEventPublisherFactory;
    private final AccommodationAssignmentPolicy accommodationAssignmentPolicy;
    private final AccommodationTypeAvailabilityPolicy accommodationTypeAvailabilityPolicy;


    public AccommodationTypeAvailability createAccommodationTypeAvailabilityFactory(AggregateId propertyId, AggregateId accommodationTypeId) {
        AccommodationTypeAvailability aggregate = new AccommodationTypeAvailability(propertyId, accommodationTypeId);
        aggregate.setPolicies(accommodationAssignmentPolicy, accommodationTypeAvailabilityPolicy);
        aggregate.setEventPublisher(domainEventPublisherFactory.getEventPublisher());
        return aggregate;
    }
}
