package pl.app.learning.reference.application.port.in;

import pl.app.learning.comment.application.port.in.command.CreateCommentContainerCommand;
import pl.app.learning.reference.application.port.in.command.CreateReferenceContainerCommand;

import java.util.UUID;

public interface CreateReferenceContainerUseCase {
    UUID create(CreateReferenceContainerCommand command);
}
