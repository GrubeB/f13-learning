package pl.app.learning.topic.application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.cqrs.command.annotation.CommandHandlerAnnotation;
import pl.app.common.cqrs.command.annotation.CommandHandlingAnnotation;
import pl.app.common.ddd.AggregateId;
import pl.app.common.search_criteria.Operator;
import pl.app.common.search_criteria.SearchCriteria;
import pl.app.common.search_criteria.SearchCriteriaItem;
import pl.app.learning.category.query.CategoryQueryService;
import pl.app.learning.topic.application.domain.Topic;
import pl.app.learning.topic.application.port.in.AddCategoryToTopicUseCase;
import pl.app.learning.topic.application.port.in.RemoveCategoryFromTopicUseCase;
import pl.app.learning.topic.application.port.in.command.AddCategoryToTopicCommand;
import pl.app.learning.topic.application.port.in.command.RemoveCategoryFromTopicCommand;
import pl.app.learning.topic.application.port.out.TopicDomainRepositoryPort;

import java.util.List;
import java.util.UUID;

@CommandHandlerAnnotation
@Component
@RequiredArgsConstructor
@Transactional
class TopicCategoryService implements
        AddCategoryToTopicUseCase,
        RemoveCategoryFromTopicUseCase {
    private final TopicDomainRepositoryPort repositoryPort;
    private final CategoryQueryService categoryQueryService;

    @Override
    @CommandHandlingAnnotation
    public void addCategory(AddCategoryToTopicCommand command) {
        Topic aggregate = repositoryPort.load(new AggregateId(command.getTopicId()));
        List<AggregateId> categories = categoryQueryService.fetchByCriteria(new SearchCriteria(List.of(
                new SearchCriteriaItem("id", Operator.IN, command.getCategoryIds().stream().map(UUID::toString).toList())
        )), AggregateId.class);
        aggregate.addCategories(categories);
        repositoryPort.save(aggregate);
    }

    @Override
    @CommandHandlingAnnotation
    public void removeCategory(RemoveCategoryFromTopicCommand command) {
        Topic aggregate = repositoryPort.load(new AggregateId(command.getTopicId()));
        aggregate.removeCategories(command.getCategoryIds().stream().map(AggregateId::new).toList());
        repositoryPort.save(aggregate);
    }
}
