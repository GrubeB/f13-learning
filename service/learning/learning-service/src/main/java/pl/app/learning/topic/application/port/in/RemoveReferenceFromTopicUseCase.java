package pl.app.learning.topic.application.port.in;

import pl.app.learning.topic.application.port.in.command.RemoveReferenceFromTopicCommand;

public interface RemoveReferenceFromTopicUseCase {
    void removeReference(RemoveReferenceFromTopicCommand command);
}
