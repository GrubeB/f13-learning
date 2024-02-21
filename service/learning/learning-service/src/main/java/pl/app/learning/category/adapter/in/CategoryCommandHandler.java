package pl.app.learning.category.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.app.common.cqrs.command.annotation.CommandHandlerAnnotation;
import pl.app.common.cqrs.command.annotation.CommandHandlingAnnotation;
import pl.app.learning.category.application.port.in.*;
import pl.app.learning.category.application.port.in.command.*;

import java.util.UUID;

@CommandHandlerAnnotation
@Component
@RequiredArgsConstructor
public class CategoryCommandHandler {
    private final CreateCategoryUseCase createCategoryUseCase;

    private final AddChildCategoryUseCase addChildCategoryUseCase;
    private final AddParentCategoryUseCase addParentCategoryUseCase;
    private final ChangeCategoryStatusUseCase changeCategoryStatusUseCase;
    private final DeleteCategoryUseCase deleteCategoryUseCase;
    private final RemoveChildCategoryUseCase removeChildCategoryUseCase;
    private final RemoveParentCategoryUseCase removeParentCategoryUseCase;
    private final UpdateCategoryUseCase updateCategoryUseCase;

    @CommandHandlingAnnotation
    public UUID handle(CreateCategoryCommand command) {
        return createCategoryUseCase.createCategory(command);
    }

    @CommandHandlingAnnotation
    public void handle(AddChildCategoryCommand command) {
        addChildCategoryUseCase.addChildCategory(command);
    }

    @CommandHandlingAnnotation
    public void handle(AddParentCategoryCommand command) {
        addParentCategoryUseCase.addParentCategory(command);
    }

    @CommandHandlingAnnotation
    public void handle(ChangeStatusCategoryCommand command) {
        changeCategoryStatusUseCase.changeStatus(command);
    }

    @CommandHandlingAnnotation
    public void handle(DeleteCategoryCommand command) {
        deleteCategoryUseCase.deleteCategory(command);
    }

    @CommandHandlingAnnotation
    public void handle(RemoveChildCategoryCommand command) {
        removeChildCategoryUseCase.removeChildCategory(command);
    }

    @CommandHandlingAnnotation
    public void handle(RemoveParentCategoryCommand command) {
        removeParentCategoryUseCase.removeParentCategory(command);
    }

    @CommandHandlingAnnotation
    public void handle(UpdateCategoryCommand command) {
        updateCategoryUseCase.updateCategory(command);
    }
}
