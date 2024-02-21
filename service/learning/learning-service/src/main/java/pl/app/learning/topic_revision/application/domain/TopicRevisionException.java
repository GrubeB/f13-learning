package pl.app.learning.topic_revision.application.domain;


import pl.app.common.shared.exception.NotFoundException;

import java.util.UUID;


public interface TopicRevisionException {
    class NotFoundTopicRevisionException extends NotFoundException {
        public NotFoundTopicRevisionException() {
            super("not found topic revision");
        }

        public NotFoundTopicRevisionException(String message) {
            super(message);
        }

        public static NotFoundTopicRevisionException fromId(UUID topicId) {
            return new NotFoundTopicRevisionException("not found topic revision with id: " + topicId);
        }
    }
}
