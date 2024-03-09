package pl.app.authorization.user.application.port.in;

import java.util.UUID;

public interface ChangePasswordUseCase {
    void changePassword(ChangePasswordCommand command);
}
