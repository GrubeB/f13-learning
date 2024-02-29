package pl.app.learning.group_revision.application.domain;


import pl.app.common.shared.exception.NotFoundException;

import java.util.UUID;


public interface GroupRevisionException {
    class NotFoundGroupRevisionException extends NotFoundException {
        public NotFoundGroupRevisionException() {
            super("not found group revision");
        }

        public NotFoundGroupRevisionException(String message) {
            super(message);
        }

        public static NotFoundGroupRevisionException fromId(UUID topicId) {
            return new NotFoundGroupRevisionException("not found group revision with id: " + topicId);
        }
    }
}
