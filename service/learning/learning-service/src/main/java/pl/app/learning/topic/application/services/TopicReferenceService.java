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
import pl.app.learning.reference.query.ReferenceQueryService;
import pl.app.learning.topic.application.domain.Topic;
import pl.app.learning.topic.application.port.in.AddReferenceToTopicUseCase;
import pl.app.learning.topic.application.port.in.RemoveReferenceFromTopicUseCase;
import pl.app.learning.topic.application.port.in.command.AddReferenceToTopicCommand;
import pl.app.learning.topic.application.port.in.command.RemoveReferenceFromTopicCommand;
import pl.app.learning.topic.application.port.out.TopicDomainRepositoryPort;

import java.util.List;
import java.util.UUID;

@CommandHandlerAnnotation
@Component
@RequiredArgsConstructor
@Transactional
class TopicReferenceService implements
        AddReferenceToTopicUseCase,
        RemoveReferenceFromTopicUseCase {
    private final TopicDomainRepositoryPort repositoryPort;
    private final ReferenceQueryService referenceQueryService;

    @Override
    @CommandHandlingAnnotation
    public void addReference(AddReferenceToTopicCommand command) {
        Topic aggregate = repositoryPort.load(new AggregateId(command.getTopicId()));
        List<AggregateId> references = referenceQueryService.fetchByCriteria(new SearchCriteria(List.of(
                new SearchCriteriaItem("id", Operator.IN, command.getReferenceIds().stream().map(UUID::toString).toList())
        )), AggregateId.class);
        aggregate.addReferences(references);
        repositoryPort.save(aggregate);
    }

    @Override
    @CommandHandlingAnnotation
    public void removeReference(RemoveReferenceFromTopicCommand command) {
        Topic aggregate = repositoryPort.load(new AggregateId(command.getTopicId()));
        aggregate.removeReferences(command.getReferenceIds().stream().map(AggregateId::new).toList());
        repositoryPort.save(aggregate);
    }

}
