package pl.app.learning.group.application.port.in;

import pl.app.learning.group.application.port.in.command.DeleteGroupCommand;

public interface DeleteGroupUseCase {
    void delete(DeleteGroupCommand command);
}
