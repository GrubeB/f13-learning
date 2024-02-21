package pl.app.learning.category.application.port.in;

import pl.app.learning.category.application.port.in.command.RemoveParentCategoryCommand;

public interface RemoveParentCategoryUseCase {
    void removeParentCategory(RemoveParentCategoryCommand command);
}
