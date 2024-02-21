package pl.app.learning.topic.application.port.in;

import pl.app.learning.topic.application.port.in.command.UpdateTopicCommand;

public interface UpdateTopicUseCase {
    void update(UpdateTopicCommand command);
}
