package pl.app.learning.topic.application.port.in;

import pl.app.learning.topic.application.port.in.command.DeleteTopicCommand;

public interface DeleteTopicUseCase {
    void deleteTopic(DeleteTopicCommand command);
}
