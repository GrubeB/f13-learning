package pl.app.learning.reference.application.port.in;

import pl.app.learning.reference.application.port.in.command.AddUserDislikeCommand;

public interface AddUserDislikeUseCase {
    void addDislike(AddUserDislikeCommand command);
}
