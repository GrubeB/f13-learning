package pl.app.learning.topic.application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.ddd.AggregateId;
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

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Transactional
class TopicService implements
        UpdateTopicUseCase,
        ChangeTopicStatusUseCase,
        DeleteTopicUseCase,
        CreateTopicUseCase {
    private final TopicFactory factory;
    private final TopicDomainRepositoryPort repositoryPort;

    @Override
    public UUID createTopic(CreateTopicCommand command) {
        Topic newTopic = factory.create(command.getName(), command.getContent(), command.getCategoryIds());
        repositoryPort.save(newTopic);
        return newTopic.getId();
    }

    @Override
    public void deleteTopic(DeleteTopicCommand command) {
        repositoryPort.delete(new AggregateId(command.getTopicId()));
    }

    @Override
    public void update(UpdateTopicCommand command) {
        Topic aggregate = repositoryPort.load(new AggregateId(command.getTopicId()));
        aggregate.updateContent(command.getName(), command.getContent());
        repositoryPort.save(aggregate);
    }

    @Override
    public void changeStatus(ChangeTopicStatusCommand command) {
        Topic aggregate = repositoryPort.load(new AggregateId(command.getTopicId()));
        aggregate.changeStatus(command.getStatus());
        repositoryPort.save(aggregate);
    }
}
