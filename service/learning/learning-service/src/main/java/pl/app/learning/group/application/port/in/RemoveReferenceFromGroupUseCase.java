package pl.app.learning.group.application.port.in;

import pl.app.learning.group.application.port.in.command.RemoveReferenceFromGroupCommand;

public interface RemoveReferenceFromGroupUseCase {
    void removeReference(RemoveReferenceFromGroupCommand command);
}
