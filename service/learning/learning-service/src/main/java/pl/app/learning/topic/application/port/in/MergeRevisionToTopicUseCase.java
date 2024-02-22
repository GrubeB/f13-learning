package pl.app.learning.topic.application.port.in;

import pl.app.learning.topic.application.port.in.command.MergeRevisionToTopicCommand;

public interface MergeRevisionToTopicUseCase {
    void merge(MergeRevisionToTopicCommand command);
}
