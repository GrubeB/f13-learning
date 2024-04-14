package pl.app.learning.progress.application.port.out;


import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.annotation.DomainRepositoryAnnotation;
import pl.app.learning.progress.application.domain.ProgressContainer;
import pl.app.learning.voting.application.domain.DomainObjectType;

@DomainRepositoryAnnotation
public interface ProgressContainerRepositoryPort {
    ProgressContainer load(AggregateId domainObject, DomainObjectType domainObjectType);

    void save(ProgressContainer aggregate);
}
