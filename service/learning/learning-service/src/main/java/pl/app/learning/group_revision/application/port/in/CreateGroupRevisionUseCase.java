package pl.app.learning.group_revision.application.port.in;

import pl.app.learning.group_revision.application.port.in.command.CreateGroupRevisionCommand;
import pl.app.learning.topic_revision.application.port.in.command.CreateTopicRevisionCommand;

import java.util.UUID;

public interface CreateGroupRevisionUseCase {
    UUID create(CreateGroupRevisionCommand command);
}
