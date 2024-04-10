package pl.app.learning.group.application.port.out;


import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.annotation.DomainRepositoryAnnotation;

@DomainRepositoryAnnotation
public interface CreateGroupVotingPort {
    AggregateId createVoting(AggregateId aggregateId);
}
