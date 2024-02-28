package pl.app.learning.group.application.port.in;

import pl.app.learning.group.application.port.in.command.MergeRevisionToGroupCommand;

public interface MergeRevisionToGroupUseCase {
    void merge(MergeRevisionToGroupCommand command);
}
