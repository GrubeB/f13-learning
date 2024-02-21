package pl.app.learning.category.application.port.in;

import pl.app.learning.category.application.port.in.command.CreateCategoryCommand;
import pl.app.learning.category.application.port.in.command.UpdateCategoryCommand;

import java.util.UUID;

public interface UpdateCategoryUseCase {
    void updateCategory(UpdateCategoryCommand command);
}
