package pl.app.learning.path.application.port.out;


import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.annotation.DomainRepositoryAnnotation;
import pl.app.learning.path.application.domain.Path;

@DomainRepositoryAnnotation
public interface PathDomainRepositoryPort {
    Path load(AggregateId aggregateId);

    void save(Path aggregate);

    void delete(AggregateId aggregateId);
}
