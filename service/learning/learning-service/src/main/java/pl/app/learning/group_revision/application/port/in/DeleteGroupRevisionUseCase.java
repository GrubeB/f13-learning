package pl.app.learning.group_revision.application.port.in;

import pl.app.learning.group_revision.application.port.in.command.DeleteGroupRevisionCommand;

public interface DeleteGroupRevisionUseCase {
    void delete(DeleteGroupRevisionCommand command);
}
