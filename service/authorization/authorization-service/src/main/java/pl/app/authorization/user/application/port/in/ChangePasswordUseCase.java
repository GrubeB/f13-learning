package pl.app.authorization.user.application.port.in;

import pl.app.authorization.user.application.port.in.command.ChangePasswordCommand;

public interface ChangePasswordUseCase {
    void changePassword(ChangePasswordCommand command);
}
