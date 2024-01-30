package pl.app.property.accommodation_type.application.domain;


import lombok.RequiredArgsConstructor;
import pl.app.ddd.AggregateId;
import pl.app.ddd.annotation.FactoryAnnotation;
import pl.app.ddd.event.DomainEventPublisher;

@FactoryAnnotation
@RequiredArgsConstructor
public class AccommodationTypeFactory {
    private final DomainEventPublisher domainEventPublisher;

    public AccommodationType createAccommodationType(AggregateId propertyId) {
        return new AccommodationType(domainEventPublisher, propertyId);
    }
}
