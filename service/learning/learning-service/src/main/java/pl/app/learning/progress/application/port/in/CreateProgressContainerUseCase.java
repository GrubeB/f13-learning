package pl.app.learning.progress.application.port.in;

import java.util.UUID;

public interface CreateProgressContainerUseCase {
    UUID create(CreateProgressContainerCommand command);
}
