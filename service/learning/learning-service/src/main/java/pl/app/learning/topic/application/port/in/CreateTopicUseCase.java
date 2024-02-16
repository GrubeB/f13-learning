package pl.app.learning.topic.application.port.in;

import pl.app.common.ddd.AggregateId;
import pl.app.learning.topic.application.port.in.command.CreateTopicCommand;

import java.util.UUID;

public interface CreateTopicUseCase {
    AggregateId createTopic(CreateTopicCommand command);
}
