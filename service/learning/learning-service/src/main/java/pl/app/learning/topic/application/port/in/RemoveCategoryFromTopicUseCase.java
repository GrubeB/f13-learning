package pl.app.learning.topic.application.port.in;

import pl.app.learning.topic.application.port.in.command.RemoveCategoryFromTopicCommand;

public interface RemoveCategoryFromTopicUseCase {
    void removeCategory(RemoveCategoryFromTopicCommand command);
}
