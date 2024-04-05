package pl.app.learning.reference.application.port.out;


import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.annotation.DomainRepositoryAnnotation;
import pl.app.learning.reference.application.domain.Reference;

@DomainRepositoryAnnotation
public interface CreateVotingPort {
    AggregateId createVoting(AggregateId aggregateId);
}
