package pl.app.learning.voting.application.port.in;

import pl.app.learning.voting.application.port.in.command.RemoveUserLikeAndDislikeCommand;

public interface RemoveUserLikeAndDislikeUseCase {
    void removeLikeAndDislike(RemoveUserLikeAndDislikeCommand command);
}
