package pl.app.learning.group.application.domain;


import pl.app.common.shared.exception.NotFoundException;
import pl.app.common.shared.exception.ValidationException;

import java.util.UUID;


public interface GroupException {
    class NotFoundGroupException extends NotFoundException {
        public NotFoundGroupException() {
            super("not found group");
        }

        public NotFoundGroupException(String message) {
            super(message);
        }

        public static NotFoundGroupException fromId(UUID topicId) {
            return new NotFoundGroupException("not found group with id: " + topicId);
        }
    }

    class NotFoundGroupSnapshotException extends NotFoundException {
        public NotFoundGroupSnapshotException() {
            super("not found group snapshot");
        }

        public NotFoundGroupSnapshotException(String message) {
            super(message);
        }

        public static NotFoundGroupSnapshotException fromSnapshotNumber(Long snapshotNumber) {
            return new NotFoundGroupSnapshotException("not found group snapshot with number: " + snapshotNumber);
        }
    }

    class GroupWrongStatusException extends ValidationException {
        public GroupWrongStatusException() {
            super("group has wrong status to process command");
        }

        public GroupWrongStatusException(String message) {
            super(message);
        }
    }
}
