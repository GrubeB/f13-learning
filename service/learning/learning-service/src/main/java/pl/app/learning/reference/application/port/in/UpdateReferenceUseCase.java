package pl.app.learning.reference.application.port.in;

import pl.app.learning.reference.application.port.in.command.UpdateReferenceCommand;

public interface UpdateReferenceUseCase {
    void updateReference(UpdateReferenceCommand command);
}
