package pl.app.authorization.role.application.port.in;

public interface RemovePermissionFromRoleUseCase {
    void removePermission(RemovePermissionFromRoleCommand command);
}
