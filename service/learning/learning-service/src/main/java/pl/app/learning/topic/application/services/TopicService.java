package pl.app.learning.topic.application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.ddd.AggregateId;
import pl.app.learning.topic.application.domain.Topic;
import pl.app.learning.topic.application.domain.TopicFactory;
import pl.app.learning.topic.application.port.in.CreateTopicUseCase;
import pl.app.learning.topic.application.port.in.command.CreateTopicCommand;
import pl.app.learning.topic.application.port.out.TopicDomainRepositoryPort;

@Component
@RequiredArgsConstructor
@Transactional
class TopicService implements
        CreateTopicUseCase {
    private final TopicFactory factory;
    private final TopicDomainRepositoryPort repositoryPort;

    @Override
    public AggregateId createTopic(CreateTopicCommand command) {
        Topic newTopic = factory.create(command.getName());
        repositoryPort.save(newTopic);
        return newTopic.getAggregateId();
    }
}
