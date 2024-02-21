package pl.app.learning.category.application.port.in;

import pl.app.learning.category.application.port.in.command.CreateCategoryCommand;

import java.util.UUID;

public interface CreateCategoryUseCase {
    UUID createCategory(CreateCategoryCommand command);
}
