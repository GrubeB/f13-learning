package pl.app.learning.group.application.port.in;

import pl.app.learning.group.application.port.in.command.UpdateGroupCommand;

import java.util.UUID;

public interface UpdateGroupUseCase {
    void update(UpdateGroupCommand command);
}
