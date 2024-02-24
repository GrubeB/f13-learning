package pl.app.property.accommodation_type.application.domain;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.annotation.FactoryAnnotation;
import pl.app.common.ddd.event.DomainEventPublisher;
import pl.app.common.ddd.event.DomainEventPublisherFactory;

@FactoryAnnotation
@Component
@RequiredArgsConstructor
public class AccommodationTypeFactory {
    private final DomainEventPublisherFactory domainEventPublisherFactory;

    public AccommodationType createAccommodationType(AggregateId propertyId) {
        AccommodationType aggregate = new AccommodationType(domainEventPublisherFactory.getEventPublisher(), propertyId);
        return aggregate;
    }
}
