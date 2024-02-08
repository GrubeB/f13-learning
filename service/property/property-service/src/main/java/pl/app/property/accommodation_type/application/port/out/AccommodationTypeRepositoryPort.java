package pl.app.property.accommodation_type.application.port.out;


import pl.app.ddd.AggregateId;
import pl.app.ddd.annotation.RepositoryAnnotation;
import pl.app.property.accommodation_type.application.domain.AccommodationType;

@RepositoryAnnotation
public interface AccommodationTypeRepositoryPort {
    void save(AccommodationType aggregate);

    AccommodationType load(AggregateId aggregateId);
}
