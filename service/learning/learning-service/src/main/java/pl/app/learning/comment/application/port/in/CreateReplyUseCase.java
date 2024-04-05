package pl.app.learning.comment.application.port.in;

import pl.app.learning.comment.application.port.in.command.CreateReplyCommand;

import java.util.UUID;

public interface CreateReplyUseCase {
    UUID create(CreateReplyCommand command);
}
