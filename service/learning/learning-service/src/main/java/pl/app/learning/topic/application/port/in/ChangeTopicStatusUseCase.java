package pl.app.learning.topic.application.port.in;

import pl.app.learning.topic.application.port.in.command.ChangeTopicStatusCommand;

public interface ChangeTopicStatusUseCase {
    void changeStatus(ChangeTopicStatusCommand command);
}
