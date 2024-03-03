package pl.app.learning.topic.application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.cqrs.command.annotation.CommandHandlerAnnotation;
import pl.app.common.cqrs.command.annotation.CommandHandlingAnnotation;
import pl.app.common.ddd.AggregateId;
import pl.app.learning.category.query.CategoryQueryService;
import pl.app.learning.topic.application.domain.Topic;
import pl.app.learning.topic.application.domain.TopicFactory;
import pl.app.learning.topic.application.port.in.ChangeTopicStatusUseCase;
import pl.app.learning.topic.application.port.in.CreateTopicUseCase;
import pl.app.learning.topic.application.port.in.DeleteTopicUseCase;
import pl.app.learning.topic.application.port.in.UpdateTopicUseCase;
import pl.app.learning.topic.application.port.in.command.ChangeTopicStatusCommand;
import pl.app.learning.topic.application.port.in.command.CreateTopicCommand;
import pl.app.learning.topic.application.port.in.command.DeleteTopicCommand;
import pl.app.learning.topic.application.port.in.command.UpdateTopicCommand;
import pl.app.learning.topic.application.port.out.TopicDomainRepositoryPort;

import java.util.List;
import java.util.UUID;

@CommandHandlerAnnotation
@Component
@RequiredArgsConstructor
@Transactional
class TopicService implements
        UpdateTopicUseCase,
        ChangeTopicStatusUseCase,
        DeleteTopicUseCase,
        CreateTopicUseCase {
    private final TopicFactory factory;
    private final TopicDomainRepositoryPort repository;
    private final CategoryQueryService categoryQueryService;

    @Override
    @CommandHandlingAnnotation
    public UUID createTopic(CreateTopicCommand command) {
        Topic newTopic = factory.create(command.getName(), command.getContent(), command.getCategoryIds());
        repository.save(newTopic);
        return newTopic.getId();
    }

    @Override
    @CommandHandlingAnnotation
    public void deleteTopic(DeleteTopicCommand command) {
        repository.delete(new AggregateId(command.getTopicId()));
    }

    @Override
    @CommandHandlingAnnotation
    public void update(UpdateTopicCommand command) {
        Topic aggregate = repository.load(new AggregateId(command.getTopicId()));
        aggregate.verifyThatTopicHaveNoVerifiedStatus();
        aggregate.updateContent(command.getName(), command.getContent());
        List<AggregateId> newCategories = categoryQueryService.fetchByIds(command.getCategoryIds(), AggregateId.class);
        aggregate.setCategories(newCategories);
        repository.save(aggregate);
    }

    @Override
    @CommandHandlingAnnotation
    public void changeStatus(ChangeTopicStatusCommand command) {
        Topic aggregate = repository.load(new AggregateId(command.getTopicId()));
        aggregate.changeStatus(command.getStatus());
        repository.save(aggregate);
    }
}
