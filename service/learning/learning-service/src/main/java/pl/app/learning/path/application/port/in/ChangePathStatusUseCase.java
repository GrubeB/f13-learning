package pl.app.learning.path.application.port.in;

import pl.app.learning.path.application.port.in.command.ChangePathStatusCommand;

public interface ChangePathStatusUseCase {
    void changeStatus(ChangePathStatusCommand command);
}
