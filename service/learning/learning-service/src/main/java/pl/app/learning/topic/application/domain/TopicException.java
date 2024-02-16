package pl.app.learning.topic.application.domain;


import pl.app.common.shared.exception.NotFoundException;

import java.util.UUID;


public interface TopicException {
    class NotFoundTopicException extends NotFoundException {
        public NotFoundTopicException() {
            super("not found topic type");
        }

        public NotFoundTopicException(String message) {
            super(message);
        }

        public static NotFoundTopicException fromId(UUID topicId) {
            return new NotFoundTopicException("not found topic type with id: " + topicId);
        }
    }
}
