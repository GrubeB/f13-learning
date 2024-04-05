package pl.app.learning.voting.application.port.out;


import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.annotation.DomainRepositoryAnnotation;
import pl.app.learning.reference.application.domain.Reference;
import pl.app.learning.voting.application.domain.DomainObjectType;
import pl.app.learning.voting.application.domain.Voting;

@DomainRepositoryAnnotation
public interface VotingDomainRepositoryPort {
    Voting load(AggregateId domainObject, DomainObjectType domainObjectType);

    void save(Voting aggregate);
}
