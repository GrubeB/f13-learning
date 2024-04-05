package pl.app.learning.comment.application.port.out;


import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.annotation.DomainRepositoryAnnotation;

@DomainRepositoryAnnotation
public interface CreateCommentVotingPort {
    AggregateId createVoting(AggregateId aggregateId);
}
