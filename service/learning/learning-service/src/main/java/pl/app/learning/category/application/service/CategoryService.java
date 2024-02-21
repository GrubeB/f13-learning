
package pl.app.learning.category.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.ddd.AggregateId;
import pl.app.learning.category.application.domain.Category;
import pl.app.learning.category.application.domain.CategoryFactory;
import pl.app.learning.category.application.port.in.ChangeCategoryStatusUseCase;
import pl.app.learning.category.application.port.in.CreateCategoryUseCase;
import pl.app.learning.category.application.port.in.DeleteCategoryUseCase;
import pl.app.learning.category.application.port.in.UpdateCategoryUseCase;
import pl.app.learning.category.application.port.in.command.ChangeStatusCategoryCommand;
import pl.app.learning.category.application.port.in.command.CreateCategoryCommand;
import pl.app.learning.category.application.port.in.command.DeleteCategoryCommand;
import pl.app.learning.category.application.port.in.command.UpdateCategoryCommand;
import pl.app.learning.category.application.port.out.CategoryDomainRepositoryPort;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Transactional
class CategoryService implements
        ChangeCategoryStatusUseCase,
        UpdateCategoryUseCase,
        DeleteCategoryUseCase,
        CreateCategoryUseCase {
    private final CategoryFactory factory;
    private final CategoryDomainRepositoryPort repository;

    @Override
    public UUID createCategory(CreateCategoryCommand command) {
        Category aggregate = factory.create(command.getName(), command.getDescription());
        repository.save(aggregate);
        return aggregate.getId();
    }

    @Override
    public void deleteCategory(DeleteCategoryCommand command) {
        repository.delete(new AggregateId(command.getCategoryId()));
    }

    @Override
    public void updateCategory(UpdateCategoryCommand command) {
        Category aggregate = repository.load(new AggregateId(command.getCategoryId()));
        aggregate.updateInfo(command.getName(), command.getDescription());
        repository.save(aggregate);
    }

    @Override
    public void changeStatus(ChangeStatusCategoryCommand command) {
        Category aggregate = repository.load(new AggregateId(command.getCategoryId()));
        aggregate.setStatus(command.getStatus());
        repository.save(aggregate);
    }
}
