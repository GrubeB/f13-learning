package pl.app.learning.group.application.domain;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.annotation.FactoryAnnotation;
import pl.app.common.ddd.event.DomainEventPublisherFactory;
import pl.app.common.search_criteria.Operator;
import pl.app.common.search_criteria.SearchCriteria;
import pl.app.common.search_criteria.SearchCriteriaItem;
import pl.app.learning.category.query.CategoryQueryService;
import pl.app.learning.group.query.GroupQueryService;
import pl.app.learning.topic.query.TopicQueryService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@FactoryAnnotation
@Component
@RequiredArgsConstructor
public class GroupFactory {
    private final DomainEventPublisherFactory domainEventPublisherFactory;
    private final CategoryQueryService categoryQueryService;
    private final TopicQueryService topicQueryService;
    private final GroupQueryService groupQueryService;

    public Group create(String name, String content, List<UUID> categoryIds, List<UUID> topicIds, List<UUID> groupIds) {

        Set<AggregateId> categories = new HashSet<>(categoryQueryService.fetchByCriteria(new SearchCriteria(List.of(
                new SearchCriteriaItem("id", Operator.IN, categoryIds.stream().map(UUID::toString).toList())
        )), AggregateId.class));

        Set<AggregateId> topics = new HashSet<>(topicQueryService.fetchByCriteria(new SearchCriteria(List.of(
                new SearchCriteriaItem("id", Operator.IN, topicIds.stream().map(UUID::toString).toList())
        )), AggregateId.class));

        Set<AggregateId> groups = new HashSet<>(groupQueryService.fetchByCriteria(new SearchCriteria(List.of(
                new SearchCriteriaItem("id", Operator.IN, groupIds.stream().map(UUID::toString).toList())
        )), AggregateId.class));

        Group group = new Group(name, content, GroupStatus.DRAFT, categories, topics, groups);
        group.setEventPublisher(domainEventPublisherFactory.getEventPublisher());
        return group;
    }
}
