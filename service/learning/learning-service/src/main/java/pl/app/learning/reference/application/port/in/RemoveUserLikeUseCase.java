package pl.app.learning.reference.application.port.in;

import pl.app.learning.reference.application.port.in.command.RemoveUserLikeCommand;

public interface RemoveUserLikeUseCase {
    void removeLike(RemoveUserLikeCommand command);
}
