package pl.app.learning.topic_revision.application.port.in;

import pl.app.learning.topic_revision.application.port.in.command.DeleteTopicRevisionCommand;

public interface DeleteTopicRevisionUseCase {
    void delete(DeleteTopicRevisionCommand command);
}
