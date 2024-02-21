package pl.app.learning.topic.application.port.in;

import pl.app.learning.topic.application.port.in.command.AddReferenceToTopicCommand;

public interface AddReferenceToTopicUseCase {
    void addReference(AddReferenceToTopicCommand command);
}
