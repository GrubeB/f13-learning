package pl.app.learning.reference.application.port.in;

import pl.app.learning.reference.application.port.in.command.AddUserLikeCommand;

public interface AddUserLikeUseCase {
    void addLike(AddUserLikeCommand command);
}
