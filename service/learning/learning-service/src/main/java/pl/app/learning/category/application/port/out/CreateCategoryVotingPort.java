package pl.app.learning.category.application.port.out;


import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.annotation.DomainRepositoryAnnotation;

@DomainRepositoryAnnotation
public interface CreateCategoryVotingPort {
    AggregateId createVoting(AggregateId aggregateId);
}
