package pl.app.authorization.permision.application.domain;


import pl.app.common.shared.exception.NotFoundException;

import java.util.UUID;

public interface PermissionException {
    class NotFoundPermissionException extends NotFoundException {
        public NotFoundPermissionException() {
            super("not found permission");
        }

        public NotFoundPermissionException(String message) {
            super(message);
        }

        public static NotFoundPermissionException fromName(String name) {
            return new NotFoundPermissionException("not found permission with name: " + name);
        }
        public static NotFoundPermissionException fromId(UUID id) {
            return new NotFoundPermissionException("not found permission with id: " + id);
        }
    }
}
