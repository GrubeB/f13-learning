package pl.app.learning.category.application.port.in;

import pl.app.learning.category.application.port.in.command.RemoveChildCategoryCommand;

public interface RemoveChildCategoryUseCase {
    void removeChildCategory(RemoveChildCategoryCommand command);
}
