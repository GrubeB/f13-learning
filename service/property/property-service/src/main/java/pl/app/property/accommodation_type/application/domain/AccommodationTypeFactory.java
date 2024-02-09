package pl.app.property.accommodation_type.application.domain;


import lombok.RequiredArgsConstructor;
import pl.app.ddd.AggregateId;
import pl.app.ddd.annotation.FactoryAnnotation;
import pl.app.ddd.event.DomainEventPublisher;
import pl.app.ddd.event.DomainEventPublisherFactory;

@FactoryAnnotation
@RequiredArgsConstructor
public class AccommodationTypeFactory {
    private final DomainEventPublisherFactory domainEventPublisherFactory;

    public AccommodationType createAccommodationType(AggregateId propertyId) {
        DomainEventPublisher eventPublisher = domainEventPublisherFactory.getEventPublisher();
        return new AccommodationType(eventPublisher, propertyId);
    }
}
