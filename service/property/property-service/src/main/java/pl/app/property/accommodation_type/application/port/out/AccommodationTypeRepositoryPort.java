package pl.app.property.accommodation_type.application.port.out;


import pl.app.ddd.AggregateId;
import pl.app.ddd.annotation.DomainRepositoryAnnotation;
import pl.app.property.accommodation_type.application.domain.AccommodationType;

@DomainRepositoryAnnotation
public interface AccommodationTypeRepositoryPort {
    AccommodationType load(AggregateId aggregateId);
    void save(AccommodationType aggregate);
}
