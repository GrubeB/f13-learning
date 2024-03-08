package pl.app.authorization.role.application.port.in;

public interface AddPermissionToRoleUseCase {
    void addPermission(AddPermissionToRoleCommand command);
}
