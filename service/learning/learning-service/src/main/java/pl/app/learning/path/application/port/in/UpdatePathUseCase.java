package pl.app.learning.path.application.port.in;

import pl.app.learning.path.application.port.in.command.UpdatePathCommand;

public interface UpdatePathUseCase {
    void update(UpdatePathCommand command);
}
