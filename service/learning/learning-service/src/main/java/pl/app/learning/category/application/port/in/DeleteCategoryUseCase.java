package pl.app.learning.category.application.port.in;

import pl.app.learning.category.application.port.in.command.DeleteCategoryCommand;

public interface DeleteCategoryUseCase {
    void deleteCategory(DeleteCategoryCommand command);
}
