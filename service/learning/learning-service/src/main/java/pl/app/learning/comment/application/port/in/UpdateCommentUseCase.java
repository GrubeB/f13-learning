package pl.app.learning.comment.application.port.in;

import pl.app.learning.comment.application.port.in.command.UpdateCommentCommand;

public interface UpdateCommentUseCase {
    void update(UpdateCommentCommand command);
}
