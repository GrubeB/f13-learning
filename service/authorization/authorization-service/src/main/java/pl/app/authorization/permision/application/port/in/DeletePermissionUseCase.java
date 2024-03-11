package pl.app.authorization.permision.application.port.in;

import pl.app.authorization.permision.application.port.in.command.DeletePermissionCommand;

public interface DeletePermissionUseCase {
    void delete(DeletePermissionCommand command);
}
