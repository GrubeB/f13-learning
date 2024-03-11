package pl.app.authorization.role.application.port.in;

import java.util.UUID;

public interface CreateRoleUseCase {
    UUID create(CreateRoleCommand command);
}
