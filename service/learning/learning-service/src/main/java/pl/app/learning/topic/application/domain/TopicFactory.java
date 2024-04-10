package pl.app.learning.topic.application.domain;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.annotation.FactoryAnnotation;
import pl.app.common.ddd.event.DomainEventPublisherFactory;
import pl.app.learning.category.query.CategoryQueryService;
import pl.app.learning.topic.application.port.out.CreateTopicReferenceContainerPort;
import pl.app.learning.topic.application.port.out.CreateTopicCommentContainerPort;
import pl.app.learning.topic.application.port.out.CreateTopicVotingPort;

import java.util.List;
import java.util.UUID;

@FactoryAnnotation
@Component
@RequiredArgsConstructor
public class TopicFactory {
    private final DomainEventPublisherFactory domainEventPublisherFactory;
    private final CategoryQueryService categoryQueryService;
    private final CreateTopicCommentContainerPort createCommentContainerPort;
    private final CreateTopicVotingPort createVotingPort;
    private final CreateTopicReferenceContainerPort createReferenceContainerPort;

    public Topic create(String name, String content, List<UUID> categoryIds) {
        List<AggregateId> categories = categoryQueryService.fetchByIds(categoryIds, AggregateId.class);
        var aggregate = new Topic(name, content, TopicStatus.DRAFT, categories.stream().map(c -> new AggregateId(c.getId())).toList());
        aggregate.setEventPublisher(domainEventPublisherFactory.getEventPublisher());

        AggregateId commandContainer = createCommentContainerPort.create(aggregate.getAggregateId());
        aggregate.setCommentContainer(commandContainer);

        AggregateId referenceContainer = createReferenceContainerPort.create(aggregate.getAggregateId());
        aggregate.setReferenceContainer(referenceContainer);

        AggregateId voting = createVotingPort.createVoting(aggregate.getAggregateId());
        aggregate.setVoting(voting);

        return aggregate;
    }
}
