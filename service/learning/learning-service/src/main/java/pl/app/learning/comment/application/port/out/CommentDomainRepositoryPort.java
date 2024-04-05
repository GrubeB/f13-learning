package pl.app.learning.comment.application.port.out;


import pl.app.common.ddd.AggregateId;
import pl.app.common.ddd.annotation.DomainRepositoryAnnotation;
import pl.app.learning.comment.application.domain.CommentContainer;
import pl.app.learning.voting.application.domain.DomainObjectType;

import java.util.UUID;

@DomainRepositoryAnnotation
public interface CommentDomainRepositoryPort {
    CommentContainer load(AggregateId domainObject, DomainObjectType domainObjectType);
    CommentContainer loadByParentCommentId(UUID parentCommentId);
    CommentContainer loadByCommentId(UUID commentId);

    void save(CommentContainer aggregate);
}
