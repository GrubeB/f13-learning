package pl.app.learning.topic_revision.application.port.in;

import pl.app.learning.topic_revision.application.port.in.command.CreateTopicRevisionCommand;

import java.util.UUID;

public interface CreateTopicRevisionUseCase {
    UUID create(CreateTopicRevisionCommand command);
}
