package pl.app.authorization.user.application.port.in;

import pl.app.authorization.user.application.port.in.command.AddRoleToUserCommand;

public interface AddRoleToUserUseCase {
    void addRole(AddRoleToUserCommand command);
}
