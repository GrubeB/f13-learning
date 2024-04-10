package pl.app.learning.comment.application.domain;


import pl.app.common.shared.exception.NotFoundException;
import pl.app.learning.voting.application.domain.DomainObjectType;

import java.util.UUID;


public interface CommentException {
    class NotFoundCommentException extends NotFoundException {
        public NotFoundCommentException() {
            super("not found comment");
        }

        public NotFoundCommentException(String message) {
            super(message);
        }

        public static NotFoundCommentException fromId(UUID commentId) {
            return new NotFoundCommentException("not found comment with id: " + commentId);
        }

        public static NotFoundCommentException fromDomainObject(UUID domainObjectId, DomainObjectType domainObjectType) {
            return new NotFoundCommentException("not found comment for domain object id: " + domainObjectId + " of type: " + domainObjectType);
        }
    }
}
