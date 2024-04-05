package pl.app.learning.comment.application.port.in;

import pl.app.learning.comment.application.port.in.command.DeleteCommentCommand;

public interface DeleteCommentUseCase {
    void delete(DeleteCommentCommand command);
}
