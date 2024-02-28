package pl.app.learning.group.application.port.in;

import pl.app.learning.group.application.port.in.command.ChangeGroupStatusCommand;

public interface ChangeGroupStatusUseCase {
    void changeStatus(ChangeGroupStatusCommand command);
}
