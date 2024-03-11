package pl.app.authorization.auth.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.authorization.auth.application.port.in.AuthenticationCommand;
import pl.app.authorization.auth.application.port.in.AuthenticationCommandResponse;
import pl.app.common.cqrs.command.gateway.CommandGateway;

@RestController
@RequestMapping(AuthenticationController.resourcePath)
@RequiredArgsConstructor
public class AuthenticationController {
    public static final String resourceName = "auth";
    public static final String resourcePath = "/api/v1/" + resourceName;

    private final CommandGateway gateway;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationCommandResponse> authenticate(@RequestBody AuthenticationCommand command) {
        AuthenticationCommandResponse response = gateway.send(command);
        return ResponseEntity.ok(response);
    }
}
