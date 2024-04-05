package pl.app.learning.comment.application.port.in;

import pl.app.learning.comment.application.port.in.command.DeleteCommandCommand;

public interface DeleteCommandUseCase {
    void delete(DeleteCommandCommand command);
}
