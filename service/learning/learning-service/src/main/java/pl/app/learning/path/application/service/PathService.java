package pl.app.learning.path.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.cqrs.command.annotation.CommandHandlerAnnotation;
import pl.app.common.cqrs.command.annotation.CommandHandlingAnnotation;
import pl.app.common.ddd.AggregateId;
import pl.app.learning.category.query.CategoryQueryService;
import pl.app.learning.group.query.GroupQueryService;
import pl.app.learning.path.application.domain.ItemEntityType;
import pl.app.learning.path.application.domain.Path;
import pl.app.learning.path.application.domain.PathFactory;
import pl.app.learning.path.application.domain.PathItem;
import pl.app.learning.path.application.port.in.ChangePathStatusUseCase;
import pl.app.learning.path.application.port.in.CreatePathUseCase;
import pl.app.learning.path.application.port.in.DeletePathUseCase;
import pl.app.learning.path.application.port.in.UpdatePathUseCase;
import pl.app.learning.path.application.port.in.command.ChangePathStatusCommand;
import pl.app.learning.path.application.port.in.command.CreatePathCommand;
import pl.app.learning.path.application.port.in.command.DeletePathCommand;
import pl.app.learning.path.application.port.in.command.UpdatePathCommand;
import pl.app.learning.path.application.port.out.PathDomainRepositoryPort;
import pl.app.learning.topic.query.TopicQueryService;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CommandHandlerAnnotation
@Component
@RequiredArgsConstructor
@Transactional
class PathService implements
        ChangePathStatusUseCase,
        CreatePathUseCase,
        DeletePathUseCase,
        UpdatePathUseCase {
    private final PathFactory factory;
    private final PathDomainRepositoryPort repository;

    private final CategoryQueryService categoryQueryService;
    private final TopicQueryService topicQueryService;
    private final GroupQueryService groupQueryService;

    @Override
    @CommandHandlingAnnotation
    public void changeStatus(ChangePathStatusCommand command) {
        Path aggregate = repository.load(new AggregateId(command.getPathId()));
        aggregate.changeStatus(command.getStatus());
        repository.save(aggregate);
    }

    @Override
    @CommandHandlingAnnotation
    public UUID create(CreatePathCommand command) {
        Path aggregate = factory.create(command);
        repository.save(aggregate);
        return aggregate.getId();
    }

    @Override
    @CommandHandlingAnnotation
    public void delete(DeletePathCommand command) {
        repository.delete(new AggregateId(command.getPathId()));
    }

    @Override
    @CommandHandlingAnnotation
    public void update(UpdatePathCommand command) {
        Path aggregate = repository.load(new AggregateId(command.getPathId()));
        aggregate.verifyThatPathHaveNoVerifiedStatus();
        aggregate.updateContent(command.getName(), command.getContent());
        List<AggregateId> newCategoryIds = categoryQueryService.fetchByIds(command.getCategoryIds(), AggregateId.class);
        aggregate.setCategories(newCategoryIds);
        aggregate.setItems(mapItems(command.getItems()));
        repository.save(aggregate);
    }

    private List<PathItem> mapItems(List<UpdatePathCommand.Item> itemCommands) {
        List<UpdatePathCommand.Item> groupCommands = itemCommands.stream().filter(i -> ItemEntityType.GROUP.equals(i.getEntityType())).toList();
        Map<UUID, AggregateId> groups = groupQueryService.fetchByIds(groupCommands.stream().map(UpdatePathCommand.Item::getEntityId).toList(), AggregateId.class)
                .stream().collect(Collectors.toMap(AggregateId::getId, Function.identity()));
        List<PathItem> groupItems = groupCommands.stream()
                .map(itemCommand -> new PathItem(itemCommand.getItemId(), itemCommand.getNumber(), itemCommand.getType(), ItemEntityType.GROUP, groups.get(itemCommand.getEntityId())))

                .toList();

        List<UpdatePathCommand.Item> topicCommands = itemCommands.stream().filter(i -> ItemEntityType.TOPIC.equals(i.getEntityType())).toList();
        Map<UUID, AggregateId> topics = topicQueryService.fetchByIds(topicCommands.stream().map(UpdatePathCommand.Item::getEntityId).toList(), AggregateId.class)
                .stream().collect(Collectors.toMap(AggregateId::getId, Function.identity()));
        List<PathItem> topicItems = topicCommands.stream()
                .map(itemCommand -> new PathItem(itemCommand.getItemId(), itemCommand.getNumber(), itemCommand.getType(), ItemEntityType.TOPIC, topics.get(itemCommand.getEntityId())))
                .toList();

        return Stream.concat(groupItems.stream(), topicItems.stream())
                .collect(Collectors.toList());
    }
}
