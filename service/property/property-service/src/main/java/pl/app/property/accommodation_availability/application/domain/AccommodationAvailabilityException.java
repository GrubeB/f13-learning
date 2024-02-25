package pl.app.property.accommodation_availability.application.domain;


import pl.app.common.shared.exception.NotFoundException;
import pl.app.common.shared.exception.ValidationException;

import java.util.UUID;

public interface AccommodationAvailabilityException {
    class AccommodationNoAvailableException extends RuntimeException {
        public AccommodationNoAvailableException() {
            super("accommodation no available");
        }

        public AccommodationNoAvailableException(String message) {
            super(message);
        }

        public static AccommodationNoAvailableException fromId(UUID id) {
            return new AccommodationNoAvailableException("accommodation is no available with id: " + id);
        }
    }

    class AccommodationReservationValidationException extends ValidationException {
        public AccommodationReservationValidationException() {
            super("argument is not valid");
        }

        public AccommodationReservationValidationException(String message) {
            super(message);
        }
    }

    class AccommodationRestrictionValidationException extends ValidationException {
        public AccommodationRestrictionValidationException() {
            super("argument is not valid");
        }

        public AccommodationRestrictionValidationException(String message) {
            super(message);
        }
    }

    class NotFoundAccommodationTypeReservationException extends NotFoundException {
        public NotFoundAccommodationTypeReservationException() {
            super("not found accommodation type reservation");
        }

        public NotFoundAccommodationTypeReservationException(String message) {
            super(message);
        }

        public static NotFoundAccommodationTypeReservationException fromId(UUID accommodationTypeReservationId) {
            return new NotFoundAccommodationTypeReservationException("not found accommodation type reservation with id: " + accommodationTypeReservationId);
        }
    }

    class NotFoundAccommodationRestrictionException extends NotFoundException {
        public NotFoundAccommodationRestrictionException() {
            super("not found accommodation restriction");
        }

        public NotFoundAccommodationRestrictionException(String message) {
            super(message);
        }

        public static NotFoundAccommodationRestrictionException fromId(UUID restrictionId) {
            return new NotFoundAccommodationRestrictionException("not found accommodation restriction with id: " + restrictionId);
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

    class NotFoundAccommodationTypeAvailabilityException extends NotFoundException {
        public NotFoundAccommodationTypeAvailabilityException() {
            super("not found accommodation type availability");
        }

        public NotFoundAccommodationTypeAvailabilityException(String message) {
            super(message);
        }

        public static NotFoundAccommodationTypeAvailabilityException fromId(UUID accommodationReservationId) {
            return new NotFoundAccommodationTypeAvailabilityException("not found  accommodation type availability with id: " + accommodationReservationId);
        }
    }
}
