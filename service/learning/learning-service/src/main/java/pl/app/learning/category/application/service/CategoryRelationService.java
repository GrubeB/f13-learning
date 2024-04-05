package pl.app.learning.category.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.cqrs.command.annotation.CommandHandlerAnnotation;
import pl.app.common.cqrs.command.annotation.CommandHandlingAnnotation;
import pl.app.common.ddd.AggregateId;
import pl.app.learning.category.application.domain.Category;
import pl.app.learning.category.application.port.in.AddChildCategoryUseCase;
import pl.app.learning.category.application.port.in.AddParentCategoryUseCase;
import pl.app.learning.category.application.port.in.RemoveChildCategoryUseCase;
import pl.app.learning.category.application.port.in.RemoveParentCategoryUseCase;
import pl.app.learning.category.application.port.in.command.AddChildCategoryCommand;
import pl.app.learning.category.application.port.in.command.AddParentCategoryCommand;
import pl.app.learning.category.application.port.in.command.RemoveChildCategoryCommand;
import pl.app.learning.category.application.port.in.command.RemoveParentCategoryCommand;
import pl.app.learning.category.application.port.out.CategoryDomainRepositoryPort;
import pl.app.learning.category.query.CategoryQueryService;

@CommandHandlerAnnotation
@Component
@RequiredArgsConstructor
@Transactional
class CategoryRelationService implements
        AddChildCategoryUseCase,
        AddParentCategoryUseCase,
        RemoveChildCategoryUseCase,
        RemoveParentCategoryUseCase {
    private final CategoryDomainRepositoryPort repository;
    private final CategoryQueryService categoryQueryService;

    @Override
    @CommandHandlingAnnotation
    public void addChildCategory(AddChildCategoryCommand command) {
        Category aggregate = repository.load(new AggregateId(command.getCategoryId()));
        AggregateId childCategory = categoryQueryService.fetchById(command.getChildCategoryId(), AggregateId.class);
        aggregate.addChildCategory(childCategory);
        repository.save(aggregate);
    }

    @Override
    @CommandHandlingAnnotation
    public void addParentCategory(AddParentCategoryCommand command) {
        Category aggregate = repository.load(new AggregateId(command.getCategoryId()));
        AggregateId parentCategory = categoryQueryService.fetchById(command.getParentCategoryId(), AggregateId.class);
        aggregate.addParentCategory(parentCategory);
        repository.save(aggregate);
    }

    @Override
    @CommandHandlingAnnotation
    public void removeChildCategory(RemoveChildCategoryCommand command) {
        Category aggregate = repository.load(new AggregateId(command.getCategoryId()));
        AggregateId childCategory = categoryQueryService.fetchById(command.getChildCategoryId(), AggregateId.class);
        aggregate.removeChildCategory(childCategory);
        repository.save(aggregate);
    }

    @Override
    @CommandHandlingAnnotation
    public void removeParentCategory(RemoveParentCategoryCommand command) {
        Category aggregate = repository.load(new AggregateId(command.getCategoryId()));
        AggregateId parentCategory = categoryQueryService.fetchById(command.getParentCategoryId(), AggregateId.class);
        aggregate.removeParentCategory(parentCategory);
        repository.save(aggregate);
    }
}
