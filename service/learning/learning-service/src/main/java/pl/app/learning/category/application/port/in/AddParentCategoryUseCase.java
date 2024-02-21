package pl.app.learning.category.application.port.in;

import pl.app.learning.category.application.port.in.command.AddParentCategoryCommand;

public interface AddParentCategoryUseCase {
    void addParentCategory(AddParentCategoryCommand command);
}
