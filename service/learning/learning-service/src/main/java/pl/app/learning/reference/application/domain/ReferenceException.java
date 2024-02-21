package pl.app.learning.reference.application.domain;


import pl.app.common.shared.exception.NotFoundException;

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
}
