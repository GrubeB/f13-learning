package pl.app.learning.group.application.port.in;

import pl.app.learning.group.application.port.in.command.CreateGroupCommand;

import java.util.UUID;

public interface CreateGroupUseCase {
    UUID create(CreateGroupCommand command);
}
