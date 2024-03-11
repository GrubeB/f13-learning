package pl.app.authorization.permision.application.port.in;

import pl.app.authorization.permision.application.port.in.command.CreatePermissionCommand;

import java.util.UUID;

public interface CreatePermissionUseCase {
    UUID create(CreatePermissionCommand command);
}
