package pl.app.authorization.auth.application.port;

import pl.app.authorization.auth.application.port.in.AuthenticationCommand;
import pl.app.authorization.auth.application.port.in.AuthenticationCommandResponse;

public interface AuthenticationUseCase {
    AuthenticationCommandResponse authenticate(AuthenticationCommand command);
}
