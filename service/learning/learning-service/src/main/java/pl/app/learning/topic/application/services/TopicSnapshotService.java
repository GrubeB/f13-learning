package pl.app.learning.topic.application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.ddd.AggregateId;
import pl.app.learning.topic.application.domain.Topic;
import pl.app.learning.topic.application.domain.TopicException;
import pl.app.learning.topic.application.domain.TopicFactory;
import pl.app.learning.topic.application.domain.TopicSnapshot;
import pl.app.learning.topic.application.port.in.RevertTopicSnapshotUseCase;
import pl.app.learning.topic.application.port.in.command.RevertTopicSnapshotCommand;
import pl.app.learning.topic.application.port.out.TopicDomainRepositoryPort;

@Component
@RequiredArgsConstructor
@Transactional
class TopicSnapshotService implements
        RevertTopicSnapshotUseCase {
    private final TopicDomainRepositoryPort repositoryPort;

    @Override
    public void revertSnapshot(RevertTopicSnapshotCommand command) {
        Topic aggregate = repositoryPort.load(new AggregateId(command.getTopicId()));
        TopicSnapshot topicSnapshot = aggregate.getSnapshotBySnapshotNumber(command.getSnapshotNumber())
                .orElseThrow(() -> TopicException.NotFoundTopicSnapshotException.fromSnapshotNumber(command.getSnapshotNumber()));
        aggregate.revertSnapshot(topicSnapshot);
        repositoryPort.save(aggregate);
    }
}
