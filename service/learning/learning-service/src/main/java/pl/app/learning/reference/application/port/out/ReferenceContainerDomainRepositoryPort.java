package pl.app.learning.reference.application.port.out;


import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.annotation.DomainRepositoryAnnotation;
import pl.app.learning.reference.application.domain.ReferenceContainer;
import pl.app.learning.voting.application.domain.DomainObjectType;

@DomainRepositoryAnnotation
public interface ReferenceContainerDomainRepositoryPort {
    ReferenceContainer load(AggregateId domainObject, DomainObjectType domainObjectType);

    ReferenceContainer loadByReference(AggregateId reference);

    void save(ReferenceContainer aggregate);
}
