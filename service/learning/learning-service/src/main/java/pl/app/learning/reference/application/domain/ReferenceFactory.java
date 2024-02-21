package pl.app.learning.reference.application.domain;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.app.common.ddd.annotation.FactoryAnnotation;
import pl.app.common.ddd.event.DomainEventPublisherFactory;

import java.time.LocalDate;

@FactoryAnnotation
@Component
@RequiredArgsConstructor
public class ReferenceFactory {
    private final DomainEventPublisherFactory domainEventPublisherFactory;

    public Reference create(String author, String title, LocalDate publicationDate, String description, String link) {
        Reference aggregate = new Reference(author, title, publicationDate, description, link);
        aggregate.setEventPublisher(domainEventPublisherFactory.getEventPublisher());
        return aggregate;
    }
}
