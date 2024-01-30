package pl.app.property.accommodation_type.application.domain;



import pl.app.common.shared.exception.NotFoundException;
import pl.app.common.shared.exception.ValidationException;

import java.util.UUID;


public interface AccommodationTypeException {
    class NotFoundAccommodationTypeException extends NotFoundException {
        public NotFoundAccommodationTypeException() {
            super("not found accommodation type");
        }

        public NotFoundAccommodationTypeException(String message) {
            super(message);
        }

        public static NotFoundAccommodationTypeException fromId(UUID accommodationId) {
            return new NotFoundAccommodationTypeException("not found accommodation type with id: " + accommodationId);
        }
    }
    class NotFoundAccommodationException extends NotFoundException {
        public NotFoundAccommodationException() {
            super("not found accommodation");
        }

        public NotFoundAccommodationException(String message) {
            super(message);
        }

        public static NotFoundAccommodationException fromId(UUID accommodationId) {
            return new NotFoundAccommodationException("not found accommodation with id: " + accommodationId);
        }
    }
    class DuplicatedAccommodationNameException extends ValidationException {
        public DuplicatedAccommodationNameException() {
            super("accommodation should have unique names");
        }

        public DuplicatedAccommodationNameException(String message) {
            super(message);
        }
    }
}
