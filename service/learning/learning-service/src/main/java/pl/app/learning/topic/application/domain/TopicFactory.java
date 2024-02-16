package pl.app.learning.topic.application.domain;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.app.common.ddd.annotation.FactoryAnnotation;
import pl.app.common.ddd.event.DomainEventPublisher;
import pl.app.common.ddd.event.DomainEventPublisherFactory;

@FactoryAnnotation
@Component
@RequiredArgsConstructor
public class TopicFactory {
    private final DomainEventPublisherFactory domainEventPublisherFactory;

    public Topic create(String name) {
        Topic topic = new Topic(name);
        DomainEventPublisher eventPublisher = domainEventPublisherFactory.getEventPublisher();
        topic.setEventPublisher(eventPublisher);
        return topic;
    }
}
