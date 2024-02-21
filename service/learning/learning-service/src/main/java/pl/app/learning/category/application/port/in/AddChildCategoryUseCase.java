package pl.app.learning.category.application.port.in;

import pl.app.learning.category.application.port.in.command.AddChildCategoryCommand;

public interface AddChildCategoryUseCase {
    void addChildCategory(AddChildCategoryCommand command);
}
