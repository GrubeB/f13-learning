package pl.app.learning.category.application.domain;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.app.common.ddd.annotation.FactoryAnnotation;
import pl.app.common.ddd.event.DomainEventPublisherFactory;

@FactoryAnnotation
@Component
@RequiredArgsConstructor
public class CategoryFactory {
    private final DomainEventPublisherFactory domainEventPublisherFactory;

    public Category create(String name, String description) {
        Category aggregate = new Category(name, description);
        aggregate.setEventPublisher(domainEventPublisherFactory.getEventPublisher());
        return aggregate;
    }
}
