package pl.app.authorization.user.application.port.in;

public interface RemoveRoleFromUserUseCase {
    void removeRole(RemoveRoleFromUserCommand command);
}
