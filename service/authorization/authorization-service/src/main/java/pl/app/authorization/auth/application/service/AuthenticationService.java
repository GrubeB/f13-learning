package pl.app.authorization.auth.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import pl.app.authorization.auth.application.port.AuthenticationUseCase;
import pl.app.authorization.auth.application.port.in.AuthenticationCommand;
import pl.app.authorization.auth.application.port.in.AuthenticationCommandResponse;
import pl.app.common.cqrs.command.annotation.CommandHandlerAnnotation;
import pl.app.common.cqrs.command.annotation.CommandHandlingAnnotation;
import pl.app.common.security.token.TokenService;

@CommandHandlerAnnotation
@Component("pl.app.authorization.auth.application.service.AuthenticationService")
@RequiredArgsConstructor
public class AuthenticationService implements
        AuthenticationUseCase {
    private final pl.app.common.security.authentication.AuthenticationService service;
    private final TokenService tokenService;

    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    @Override
    @CommandHandlingAnnotation
    public AuthenticationCommandResponse authenticate(AuthenticationCommand command) {
        Authentication authentication = service.authenticate(command.getEmail(), command.getPassword());
        return new AuthenticationCommandResponse(
                tokenService.generateToken(authentication),
                "Bearer",
                String.valueOf(jwtExpiration)
        );
    }
}
