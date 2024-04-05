package pl.app.learning.comment.application.port.in;

import pl.app.learning.comment.application.port.in.command.UpdateCommandCommand;

public interface UpdateCommandUseCase {
    void update(UpdateCommandCommand command);
}
