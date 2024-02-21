package pl.app.learning.topic.application.domain;


import pl.app.common.shared.exception.NotFoundException;
import pl.app.common.shared.exception.ValidationException;

import java.util.UUID;


public interface TopicException {
    class NotFoundTopicException extends NotFoundException {
        public NotFoundTopicException() {
            super("not found topic");
        }

        public NotFoundTopicException(String message) {
            super(message);
        }

        public static NotFoundTopicException fromId(UUID topicId) {
            return new NotFoundTopicException("not found topic with id: " + topicId);
        }
    }
    class NotFoundTopicSnapshotException extends NotFoundException {
        public NotFoundTopicSnapshotException() {
            super("not found topic snapshot");
        }

        public NotFoundTopicSnapshotException(String message) {
            super(message);
        }

        public static NotFoundTopicSnapshotException fromSnapshotNumber(Long snapshotNumber) {
            return new NotFoundTopicSnapshotException("not found topic snapshot with number: " + snapshotNumber);
        }
    }
    class TopicWrongStatusException extends ValidationException {
        public TopicWrongStatusException() {
            super("topic has wrong status to process command");
        }

        public TopicWrongStatusException(String message) {
            super(message);
        }
    }
}
