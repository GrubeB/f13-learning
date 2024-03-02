package pl.app.learning.topic.application.domain;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.annotation.FactoryAnnotation;
import pl.app.common.ddd.event.DomainEventPublisherFactory;
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
        List<AggregateId> categories = categoryQueryService.fetchByIds(categoryIds, AggregateId.class);
        Topic topic = new Topic(name, content, TopicStatus.DRAFT, categories.stream().map(c -> new AggregateId(c.getId())).toList());
        topic.setEventPublisher(domainEventPublisherFactory.getEventPublisher());
        return topic;
    }
}
