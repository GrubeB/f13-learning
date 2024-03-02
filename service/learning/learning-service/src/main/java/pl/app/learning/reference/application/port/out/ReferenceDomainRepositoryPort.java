package pl.app.learning.reference.application.port.out;


import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.annotation.DomainRepositoryAnnotation;
import pl.app.learning.reference.application.domain.Reference;

@DomainRepositoryAnnotation
public interface ReferenceDomainRepositoryPort {
    Reference load(AggregateId aggregateId);

    void save(Reference aggregate);

    void delete(AggregateId aggregateId);
}
