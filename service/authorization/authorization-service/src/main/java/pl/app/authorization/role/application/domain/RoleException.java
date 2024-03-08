package pl.app.authorization.role.application.domain;


import pl.app.authorization.permision.application.domain.PermissionException;
import pl.app.common.shared.exception.NotFoundException;

import java.util.UUID;

public interface RoleException {
    class NotFoundRoleException extends NotFoundException {
        public NotFoundRoleException() {
            super("not found role");
        }

        public NotFoundRoleException(String message) {
            super(message);
        }

        public static PermissionException.NotFoundPermissionException fromName(String name) {
            return new PermissionException.NotFoundPermissionException("not found role with name: " + name);
        }

        public static PermissionException.NotFoundPermissionException fromId(UUID id) {
            return new PermissionException.NotFoundPermissionException("not found role with id: " + id);
        }
    }
}
