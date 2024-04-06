package pl.app.authorization.user.application.port.in;

import pl.app.authorization.user.application.port.in.command.RemoveRoleFromUserCommand;

public interface RemoveRoleFromUserUseCase {
    void removeRole(RemoveRoleFromUserCommand command);
}
