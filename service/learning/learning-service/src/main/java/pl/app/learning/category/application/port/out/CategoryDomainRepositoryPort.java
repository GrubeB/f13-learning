package pl.app.learning.category.application.port.out;


import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.annotation.DomainRepositoryAnnotation;
import pl.app.learning.category.application.domain.Category;

@DomainRepositoryAnnotation
public interface CategoryDomainRepositoryPort {
    Category load(AggregateId aggregateId);

    void save(Category aggregate);

    void delete(AggregateId aggregateId);
}
