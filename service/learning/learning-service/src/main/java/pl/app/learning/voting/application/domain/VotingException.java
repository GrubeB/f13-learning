package pl.app.learning.voting.application.domain;


import pl.app.common.shared.exception.NotFoundException;

import java.util.UUID;


public interface VotingException {
    class NotFoundVotingException extends NotFoundException {
        public NotFoundVotingException() {
            super("not found voting");
        }

        public NotFoundVotingException(String message) {
            super(message);
        }

        public static NotFoundVotingException fromDomainObject(UUID domainObjectId, DomainObjectType domainObjectType) {
            return new NotFoundVotingException("not found voting for domain object id: " + domainObjectId + " of type: " + domainObjectType);
        }
    }
}
