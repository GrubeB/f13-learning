package pl.app.learning.topic.application.domain;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.annotation.FactoryAnnotation;
import pl.app.common.ddd.event.DomainEventPublisherFactory;
import pl.app.common.search_criteria.Operator;
import pl.app.common.search_criteria.SearchCriteria;
import pl.app.common.search_criteria.SearchCriteriaItem;
import pl.app.learning.category.query.CategoryQuery;
import pl.app.learning.category.query.CategoryQueryService;

import java.util.List;
import java.util.UUID;

@FactoryAnnotation
@Component
@RequiredArgsConstructor
public class TopicFactory {
    private final DomainEventPublisherFactory domainEventPublisherFactory;
    private final CategoryQueryService categoryQueryService;

    public Topic create(String name, String content, List<UUID> categoryIds) {
        List<CategoryQuery> categories = categoryQueryService.fetchByCriteria(new SearchCriteria(List.of(
                new SearchCriteriaItem("id", Operator.IN, categoryIds.stream().map(UUID::toString).toList())
        )));
        Topic topic = new Topic(name, content, TopicStatus.UNVERIFIED, categories.stream().map(c -> new AggregateId(c.getId())).toList());
        topic.setEventPublisher(domainEventPublisherFactory.getEventPublisher());
        return topic;
    }
}
