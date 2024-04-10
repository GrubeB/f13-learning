package pl.app.learning.comment.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.cqrs.command.annotation.CommandHandlerAnnotation;
import pl.app.common.cqrs.command.annotation.CommandHandlingAnnotation;
import pl.app.common.ddd.AggregateId;
import pl.app.learning.comment.application.domain.Comment;
import pl.app.learning.comment.application.domain.CommentContainer;
import pl.app.learning.comment.application.domain.CommentFactory;
import pl.app.learning.comment.application.port.in.*;
import pl.app.learning.comment.application.port.in.command.*;
import pl.app.learning.comment.application.port.out.CommentDomainRepositoryPort;

import java.util.UUID;

@CommandHandlerAnnotation
@Component
@RequiredArgsConstructor
@Transactional
class CommentService implements
        CreateCommentContainerUseCase,
        CreateCommentUseCase,
        CreateReplyUseCase,
        DeleteCommentUseCase,
        UpdateCommentUseCase {
    private final CommentDomainRepositoryPort repository;
    private final CommentFactory commentFactory;

    @Override
    @CommandHandlingAnnotation
    public UUID create(CreateCommentContainerCommand command) {
        var aggregate= new CommentContainer(new AggregateId(command.getDomainObjectId()), command.getDomainObjectType());
        repository.save(aggregate);
        return aggregate.getId();
    }

    @Override
    @CommandHandlingAnnotation
    public UUID create(CreateCommentCommand command) {
        var aggregate= repository.load(new AggregateId(command.getDomainObjectId()), command.getDomainObjectType());
        Comment comment = commentFactory.create(command.getContent(), new AggregateId(command.getUserId()));
        aggregate.addComment(comment);
        repository.save(aggregate);
        return comment.getId();
    }

    @Override
    @CommandHandlingAnnotation
    public UUID create(CreateReplyCommand command) {
        var aggregate= repository.loadByParentCommentId(command.getParentCommentId());
        Comment comment = commentFactory.create(command.getContent(), new AggregateId(command.getUserId()));
        aggregate.addComment(command.getParentCommentId(), comment);
        repository.save(aggregate);
        return comment.getId();
    }

    @Override
    @CommandHandlingAnnotation
    public void delete(DeleteCommentCommand command) {
        var aggregate = repository.loadByCommentId(command.getCommentId());
        aggregate.deleteComment(command.getCommentId());
        repository.save(aggregate);
    }

    @Override
    @CommandHandlingAnnotation
    public void update(UpdateCommentCommand command) {
        var aggregate = repository.loadByCommentId(command.getCommentId());
        aggregate.updateComment(command.getCommentId(), command.getContent());
        repository.save(aggregate);
    }
}
