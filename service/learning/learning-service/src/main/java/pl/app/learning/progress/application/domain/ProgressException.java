package pl.app.learning.progress.application.domain;


import pl.app.common.shared.exception.NotFoundException;
import pl.app.learning.voting.application.domain.DomainObjectType;

import java.util.UUID;


public interface ProgressException {
    class NotFoundProgressException extends NotFoundException {
        public NotFoundProgressException() {
            super("not found progress");
        }

        public NotFoundProgressException(String message) {
            super(message);
        }

        public static NotFoundProgressException fromDomainObject(UUID domainObjectId, DomainObjectType domainObjectType) {
            return new NotFoundProgressException("not found progress for domain object id: " + domainObjectId + " of type: " + domainObjectType);
        }
    }
}
