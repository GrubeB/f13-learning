package pl.app.learning.topic.application.port.in;

import pl.app.learning.topic.application.port.in.command.AddCategoryToTopicCommand;

public interface AddCategoryToTopicUseCase {
    void addCategory(AddCategoryToTopicCommand command);
}
