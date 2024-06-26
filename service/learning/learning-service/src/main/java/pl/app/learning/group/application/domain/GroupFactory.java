package pl.app.learning.group.application.domain;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.annotation.FactoryAnnotation;
import pl.app.common.ddd.event.DomainEventPublisherFactory;
import pl.app.learning.category.query.CategoryQueryService;
import pl.app.learning.group.application.port.out.CreateGroupCommentContainerPort;
import pl.app.learning.group.application.port.out.CreateGroupProgressContainerPort;
import pl.app.learning.group.application.port.out.CreateGroupReferenceContainerPort;
import pl.app.learning.group.application.port.out.CreateGroupVotingPort;
import pl.app.learning.group.query.GroupQueryService;
import pl.app.learning.topic.query.TopicQueryService;

import java.util.List;
import java.util.UUID;

@FactoryAnnotation
@Component
@RequiredArgsConstructor
public class GroupFactory {
    private final DomainEventPublisherFactory domainEventPublisherFactory;
    private final CategoryQueryService categoryQueryService;
    private final TopicQueryService topicQueryService;
    private final GroupQueryService groupQueryService;
    private final CreateGroupCommentContainerPort createCommentContainerPort;
    private final CreateGroupVotingPort createVotingPort;
    private final CreateGroupReferenceContainerPort createReferenceContainerPort;
    private final CreateGroupProgressContainerPort createProgressContainerPort;

    public Group create(String name, String content, List<UUID> categoryIds, List<UUID> topicIds, List<UUID> groupIds) {

        List<AggregateId> categories = categoryQueryService.fetchByIds(categoryIds, AggregateId.class);
        List<AggregateId> topics = topicQueryService.fetchByIds(topicIds, AggregateId.class);
        List<AggregateId> groups = groupQueryService.fetchByIds(groupIds, AggregateId.class);
        var aggregate = new Group(name, content, GroupStatus.DRAFT, categories, topics, groups);
        aggregate.setEventPublisher(domainEventPublisherFactory.getEventPublisher());

        AggregateId commandContainer = createCommentContainerPort.create(aggregate.getAggregateId());
        aggregate.setCommentContainer(commandContainer);

        AggregateId voting = createVotingPort.createVoting(aggregate.getAggregateId());
        aggregate.setVoting(voting);

        AggregateId referenceContainer = createReferenceContainerPort.create(aggregate.getAggregateId());
        aggregate.setReferenceContainer(referenceContainer);

        AggregateId progressContainer = createProgressContainerPort.create(aggregate.getAggregateId());
        aggregate.setProgressContainer(progressContainer);

        return aggregate;
    }
}
