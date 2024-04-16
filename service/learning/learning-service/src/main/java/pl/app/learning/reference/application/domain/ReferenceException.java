package pl.app.learning.reference.application.domain;


import pl.app.common.shared.exception.NotFoundException;
import pl.app.learning.voting.application.domain.DomainObjectType;

import java.util.UUID;


public interface ReferenceException {
    class NotFoundReferenceException extends NotFoundException {
        public NotFoundReferenceException() {
            super("not found reference");
        }

        public NotFoundReferenceException(String message) {
            super(message);
        }

        public static NotFoundReferenceException fromId(UUID referenceId) {
            return new NotFoundReferenceException("not found reference with id: " + referenceId);
        }
    }

    class NotFoundReferenceContainerException extends NotFoundException {
        public NotFoundReferenceContainerException() {
            super("not found reference container");
        }

        public NotFoundReferenceContainerException(String message) {
            super(message);
        }

        public static NotFoundReferenceContainerException fromDomainObject(UUID domainObjectId, DomainObjectType domainObjectType) {
            return new NotFoundReferenceContainerException("not found reference container for domain object id: " + domainObjectId + " of type: " + domainObjectType);
        }
    }
}
