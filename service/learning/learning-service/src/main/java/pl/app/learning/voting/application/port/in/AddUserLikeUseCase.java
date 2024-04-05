package pl.app.learning.voting.application.port.in;

import pl.app.learning.voting.application.port.in.command.AddUserLikeCommand;

public interface AddUserLikeUseCase {
    void addLike(AddUserLikeCommand command);
}
