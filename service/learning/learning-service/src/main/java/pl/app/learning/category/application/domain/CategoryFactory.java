package pl.app.learning.category.application.domain;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.annotation.FactoryAnnotation;
import pl.app.common.ddd.event.DomainEventPublisherFactory;
import pl.app.learning.category.application.port.out.CreateCategoryVotingPort;
import pl.app.learning.category.query.CategoryQueryService;

import java.util.List;
import java.util.UUID;

@FactoryAnnotation
@Component
@RequiredArgsConstructor
public class CategoryFactory {
    private final DomainEventPublisherFactory domainEventPublisherFactory;
    private final CategoryQueryService categoryQueryService;
    private final CreateCategoryVotingPort createCategoryVotingPort;
    public Category create(String name, String description, List<UUID> parentIds, List<UUID> childIds) {
        List<AggregateId> parentCategories = categoryQueryService.fetchByIds(parentIds, AggregateId.class);
        List<AggregateId> childCategories = categoryQueryService.fetchByIds(childIds, AggregateId.class);
        Category aggregate = new Category(name, description, parentCategories, childCategories);
        aggregate.setEventPublisher(domainEventPublisherFactory.getEventPublisher());

        AggregateId voting = createCategoryVotingPort.createVoting(aggregate.getAggregateId());
        aggregate.setVoting(voting);
        return aggregate;
    }
}
