package pl.app.learning.comment.application.port.in;

import pl.app.learning.comment.application.port.in.command.CreateCommentContainerCommand;

import java.util.UUID;

public interface CreateContainerUseCase {
    UUID create(CreateCommentContainerCommand command);
}
