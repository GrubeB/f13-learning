package pl.app.learning.path.application.port.in;

import pl.app.learning.path.application.port.in.command.DeletePathCommand;

public interface DeletePathUseCase {
    void delete(DeletePathCommand command);
}
