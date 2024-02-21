package pl.app.learning.reference.application.port.in;

import pl.app.learning.reference.application.port.in.command.DeleteReferenceCommand;

public interface DeleteReferenceUseCase {
    void deleteReference(DeleteReferenceCommand command);
}
