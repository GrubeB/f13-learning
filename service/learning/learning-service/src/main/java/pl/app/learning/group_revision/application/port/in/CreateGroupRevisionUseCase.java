package pl.app.learning.group_revision.application.port.in;

import pl.app.learning.group_revision.application.port.in.command.CreateGroupRevisionCommand;

import java.util.UUID;

public interface CreateGroupRevisionUseCase {
    UUID create(CreateGroupRevisionCommand command);
}
