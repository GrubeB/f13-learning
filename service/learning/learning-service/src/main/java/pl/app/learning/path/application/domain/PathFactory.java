package pl.app.learning.path.application.domain;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.annotation.FactoryAnnotation;
import pl.app.common.ddd.event.DomainEventPublisherFactory;
import pl.app.learning.category.query.CategoryQueryService;
import pl.app.learning.group.query.GroupQueryService;
import pl.app.learning.path.application.port.in.CreatePathCommand;
import pl.app.learning.topic.query.TopicQueryService;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@FactoryAnnotation
@Component
@RequiredArgsConstructor
public class PathFactory {
    private final DomainEventPublisherFactory domainEventPublisherFactory;
    private final CategoryQueryService categoryQueryService;
    private final TopicQueryService topicQueryService;
    private final GroupQueryService groupQueryService;

    public Path create(CreatePathCommand command) {
        List<AggregateId> categories = categoryQueryService.fetchByIds(command.getCategoryIds(), AggregateId.class);

        var aggregate = new Path(command.getName(), command.getContent(), PathStatus.DRAFT, categories, getItems(command.getItems()));
        aggregate.setEventPublisher(domainEventPublisherFactory.getEventPublisher());
        return aggregate;
    }

    private List<PathItem> getItems(List<CreatePathCommand.Item> itemCommands) {
        List<CreatePathCommand.Item> groupCommands = itemCommands.stream().filter(i -> ItemEntityType.GROUP.equals(i.getEntityType())).toList();
        Map<UUID, AggregateId> groups = groupQueryService.fetchByIds(groupCommands.stream().map(CreatePathCommand.Item::getEntityId).toList(), AggregateId.class)
                .stream().collect(Collectors.toMap(AggregateId::getId, Function.identity()));
        List<PathItem> groupItems = groupCommands.stream()
                .map(itemCommand -> new PathItem(itemCommand.getNumber(), itemCommand.getType(), ItemEntityType.GROUP, groups.get(itemCommand.getEntityId())))
                .toList();

        List<CreatePathCommand.Item> topicCommands = itemCommands.stream().filter(i -> ItemEntityType.TOPIC.equals(i.getEntityType())).toList();
        Map<UUID, AggregateId> topics = topicQueryService.fetchByIds(topicCommands.stream().map(CreatePathCommand.Item::getEntityId).toList(), AggregateId.class)
                .stream().collect(Collectors.toMap(AggregateId::getId, Function.identity()));
        List<PathItem> topicItems = topicCommands.stream()
                .map(itemCommand -> new PathItem(itemCommand.getNumber(), itemCommand.getType(), ItemEntityType.TOPIC, topics.get(itemCommand.getEntityId())))
                .toList();

        return Stream.concat(groupItems.stream(), topicItems.stream())
                .collect(Collectors.toList());
    }
}
