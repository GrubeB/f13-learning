package pl.app.learning.topic.application.port.in;

import pl.app.learning.topic.application.port.in.command.CreateTopicCommand;

import java.util.UUID;

public interface CreateTopicUseCase {
    UUID createTopic(CreateTopicCommand command);
}
