package pl.app.learning.comment.application.port.in;

import pl.app.learning.comment.application.port.in.command.CreateCommentCommand;

import java.util.UUID;

public interface CreateCommentUseCase {
    UUID create(CreateCommentCommand command);
}
