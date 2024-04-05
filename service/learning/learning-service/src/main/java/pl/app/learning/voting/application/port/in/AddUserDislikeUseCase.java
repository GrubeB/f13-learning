package pl.app.learning.voting.application.port.in;

import pl.app.learning.voting.application.port.in.command.AddUserDislikeCommand;

public interface AddUserDislikeUseCase {
    void addDislike(AddUserDislikeCommand command);
}
