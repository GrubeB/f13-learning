package pl.app.authorization.user.application.domain;


import pl.app.common.shared.exception.NotFoundException;
import pl.app.common.shared.exception.ValidationException;

import java.util.UUID;

public interface UserException {
    class NotFoundUserException extends NotFoundException {
        public NotFoundUserException() {
            super("not found user");
        }

        public NotFoundUserException(String message) {
            super(message);
        }

        public static NotFoundUserException fromId(UUID userId) {
            return new NotFoundUserException("not found user with id: " + userId);
        }

        public static NotFoundUserException fromEmail(String email) {
            return new NotFoundUserException("not found user with email: " + email);
        }
    }

    class PasswordValidationException extends ValidationException {
        public PasswordValidationException() {
            super("password validation exception");
        }

        public PasswordValidationException(String message) {
            super(message);
        }
    }

    class EmailValidationException extends ValidationException {
        public EmailValidationException() {
            super("email validation exception");
        }

        public EmailValidationException(String message) {
            super(message);
        }
    }
}
