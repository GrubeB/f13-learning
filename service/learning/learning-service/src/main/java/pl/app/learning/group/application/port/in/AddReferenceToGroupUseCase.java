package pl.app.learning.group.application.port.in;

import pl.app.learning.group.application.port.in.command.AddReferenceToGroupCommand;

public interface AddReferenceToGroupUseCase {
    void addReference(AddReferenceToGroupCommand command);
}
