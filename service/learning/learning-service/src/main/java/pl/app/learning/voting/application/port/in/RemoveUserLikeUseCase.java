package pl.app.learning.voting.application.port.in;

import pl.app.learning.voting.application.port.in.command.RemoveUserLikeCommand;

public interface RemoveUserLikeUseCase {
    void removeLike(RemoveUserLikeCommand command);
}
