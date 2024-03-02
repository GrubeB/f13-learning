package pl.app.learning.category.application.port.in;

import pl.app.learning.category.application.port.in.command.UpdateCategoryCommand;

public interface UpdateCategoryUseCase {
    void updateCategory(UpdateCategoryCommand command);
}
