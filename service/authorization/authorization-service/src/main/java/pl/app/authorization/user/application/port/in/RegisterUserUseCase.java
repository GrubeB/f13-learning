package pl.app.authorization.user.application.port.in;

import pl.app.authorization.user.application.port.in.command.RegisterUserCommand;

import java.util.UUID;

public interface RegisterUserUseCase {
    UUID register(RegisterUserCommand command);
}
