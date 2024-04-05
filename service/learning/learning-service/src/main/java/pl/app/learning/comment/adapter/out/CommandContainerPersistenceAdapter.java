package pl.app.learning.comment.adapter.out;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.event.DelayedDomainEventPublisher;
import pl.app.common.ddd.event.DomainEventPublisherFactory;
import pl.app.learning.comment.application.domain.CommentContainer;
import pl.app.learning.comment.application.domain.CommentException;
import pl.app.learning.comment.application.port.out.CommentDomainRepositoryPort;
import pl.app.learning.voting.application.domain.DomainObjectType;
import pl.app.learning.voting.application.domain.Voting;
import pl.app.learning.voting.application.domain.VotingException;
import pl.app.learning.voting.application.port.out.VotingDomainRepositoryPort;

import java.util.UUID;

@Component
@Transactional
@RequiredArgsConstructor
class CommandContainerPersistenceAdapter implements
        CommentDomainRepositoryPort {
    private final CommandContainerRepository repository;
    private final DomainEventPublisherFactory domainEventPublisherFactory;

    @Override
    public CommentContainer loadByParentCommentId(UUID parentCommentId) {
        var entity = repository.findByComments_EntityId(parentCommentId)
                .orElseThrow(() -> CommentException.NotFoundCommentException.fromId(parentCommentId));
        entity.setEventPublisher(domainEventPublisherFactory.getEventPublisher());
        return entity;
    }

    @Override
    public CommentContainer loadByCommentId(UUID commentId) {
        var entity = repository.findByComments_EntityId(commentId)
                .orElseThrow(() -> CommentException.NotFoundCommentException.fromId(commentId));
        entity.setEventPublisher(domainEventPublisherFactory.getEventPublisher());
        return entity;
    }
    @Override
    public CommentContainer load(AggregateId domainObject, DomainObjectType domainObjectType) {
        var entity = repository.findByDomainObject_AggregateIdAndDomainObjectType(domainObject.getId(), domainObjectType)
                .orElseThrow(() -> CommentException.NotFoundCommentException.fromDomainObject(domainObject.getId(), domainObjectType));
        entity.setEventPublisher(domainEventPublisherFactory.getEventPublisher());
        return entity;
    }

    @Override
    public void save(CommentContainer aggregate) {
        repository.saveAndFlush(aggregate);
        if (aggregate.getEventPublisher() instanceof DelayedDomainEventPublisher publisher) {
            publisher.publishDelayedEvents();
        }
    }
}
