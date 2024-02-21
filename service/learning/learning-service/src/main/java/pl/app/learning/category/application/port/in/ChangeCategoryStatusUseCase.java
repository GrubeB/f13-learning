package pl.app.learning.category.application.port.in;

import pl.app.learning.category.application.port.in.command.ChangeStatusCategoryCommand;

public interface ChangeCategoryStatusUseCase {
    void changeStatus(ChangeStatusCategoryCommand command);
}
