package pl.app.learning.reference.application.port.in;

import pl.app.learning.reference.application.port.in.command.RemoveUserDislikeCommand;

public interface RemoveUserDislikeUseCase {
    void removeDislike(RemoveUserDislikeCommand command);
}
