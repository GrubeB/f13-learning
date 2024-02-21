package pl.app.learning.category.application.domain;


import pl.app.common.shared.exception.NotFoundException;

import java.util.UUID;


public interface CategoryException {
    class NotFoundCategoryException extends NotFoundException {
        public NotFoundCategoryException() {
            super("not found category");
        }

        public NotFoundCategoryException(String message) {
            super(message);
        }

        public static NotFoundCategoryException fromId(UUID referenceId) {
            return new NotFoundCategoryException("not found category with id: " + referenceId);
        }
    }
}
