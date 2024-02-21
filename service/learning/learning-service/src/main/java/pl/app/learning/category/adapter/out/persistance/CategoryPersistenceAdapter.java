package pl.app.learning.category.adapter.out.persistance;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.event.DelayedDomainEventPublisher;
import pl.app.common.ddd.event.DomainEventPublisherFactory;
import pl.app.learning.category.application.domain.Category;
import pl.app.learning.category.application.domain.CategoryException;
import pl.app.learning.category.application.port.out.CategoryDomainRepositoryPort;

@Component
@Transactional
@RequiredArgsConstructor
class CategoryPersistenceAdapter implements
        CategoryDomainRepositoryPort {
    private final CategoryRepository repository;
    private final DomainEventPublisherFactory domainEventPublisherFactory;

    @Override
    public Category load(AggregateId aggregateId) {
        Category entity = repository.findById(aggregateId)
                .orElseThrow(() -> CategoryException.NotFoundCategoryException.fromId(aggregateId.getId()));
        entity.setEventPublisher(domainEventPublisherFactory.getEventPublisher());
        return entity;
    }

    @Override
    public void save(Category aggregate) {
        repository.saveAndFlush(aggregate);
        if (aggregate.getEventPublisher() instanceof DelayedDomainEventPublisher publisher) {
            publisher.publishDelayedEvents();
        }
    }

    @Override
    public void delete(AggregateId aggregateId) {
        repository.deleteByAggregateId_AggregateId(aggregateId.getId());
    }
}
