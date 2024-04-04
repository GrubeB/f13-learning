package pl.app.learning.reference.application.domain;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.annotation.FactoryAnnotation;
import pl.app.common.ddd.event.DomainEventPublisherFactory;
import pl.app.learning.reference.application.port.out.CreateVotingPort;

import java.time.LocalDate;

@FactoryAnnotation
@Component
@RequiredArgsConstructor
public class ReferenceFactory {
    private final DomainEventPublisherFactory domainEventPublisherFactory;
    private final CreateVotingPort createVotingPort;

    public Reference create(String author, String title, LocalDate publicationDate, String description, String link) {
        Reference aggregate = new Reference(author, title, publicationDate, description, link);
        aggregate.setEventPublisher(domainEventPublisherFactory.getEventPublisher());
        AggregateId voting = createVotingPort.createVoting(aggregate.getAggregateId());
        aggregate.setVoting(voting);
        return aggregate;
    }
}
