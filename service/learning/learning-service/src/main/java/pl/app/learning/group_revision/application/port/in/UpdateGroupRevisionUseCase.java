package pl.app.learning.group_revision.application.port.in;

import pl.app.learning.group_revision.application.port.in.command.UpdateGroupRevisionCommand;

public interface UpdateGroupRevisionUseCase {
    void update(UpdateGroupRevisionCommand command);
}
