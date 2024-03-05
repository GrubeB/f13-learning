package pl.app.learning.path.application.port.in;

import java.util.UUID;

public interface CreatePathUseCase {
    UUID create(CreatePathCommand command);
}
