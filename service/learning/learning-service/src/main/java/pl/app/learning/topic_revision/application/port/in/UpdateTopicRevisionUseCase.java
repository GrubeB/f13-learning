package pl.app.learning.topic_revision.application.port.in;

import pl.app.learning.topic_revision.application.port.in.command.UpdateTopicRevisionCommand;

public interface UpdateTopicRevisionUseCase {
    void update(UpdateTopicRevisionCommand command);
}
