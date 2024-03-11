package pl.app.learning.path.application.domain;


import pl.app.common.shared.exception.NotFoundException;
import pl.app.common.shared.exception.ValidationException;

import java.util.UUID;


public interface PathException {
    class NotFoundPathException extends NotFoundException {
        public NotFoundPathException() {
            super("not found path");
        }

        public NotFoundPathException(String message) {
            super(message);
        }

        public static NotFoundPathException fromId(UUID topicId) {
            return new NotFoundPathException("not found path with id: " + topicId);
        }
    }

    class PathWrongStatusException extends ValidationException {
        public PathWrongStatusException() {
            super("path has wrong status to process command");
        }

        public PathWrongStatusException(String message) {
            super(message);
        }
    }

    class DuplicatedTopicsInOnePathException extends ValidationException {
        public DuplicatedTopicsInOnePathException() {
            super("path should have unique topics");
        }

        public DuplicatedTopicsInOnePathException(String message) {
            super(message);
        }
    }

    class DuplicatedGroupsInOnePathException extends ValidationException {
        public DuplicatedGroupsInOnePathException() {
            super("path should have unique groups");
        }

        public DuplicatedGroupsInOnePathException(String message) {
            super(message);
        }
    }
}
