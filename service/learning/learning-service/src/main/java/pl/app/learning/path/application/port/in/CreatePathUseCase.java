package pl.app.learning.path.application.port.in;

import pl.app.learning.path.application.port.in.command.CreatePathCommand;

import java.util.UUID;

public interface CreatePathUseCase {
    UUID create(CreatePathCommand command);
}
