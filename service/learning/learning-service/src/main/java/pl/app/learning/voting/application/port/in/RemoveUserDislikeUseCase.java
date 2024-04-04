package pl.app.learning.voting.application.port.in;

import pl.app.learning.voting.application.port.in.command.RemoveUserDislikeCommand;

public interface RemoveUserDislikeUseCase {
    void removeDislike(RemoveUserDislikeCommand command);
}
