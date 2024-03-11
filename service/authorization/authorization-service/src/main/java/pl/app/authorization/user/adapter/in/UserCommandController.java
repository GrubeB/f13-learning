package pl.app.authorization.user.adapter.in;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.authorization.user.application.port.in.AddRoleToUserCommand;
import pl.app.authorization.user.application.port.in.ChangePasswordCommand;
import pl.app.authorization.user.application.port.in.RegisterUserCommand;
import pl.app.authorization.user.application.port.in.RemoveRoleFromUserCommand;
import pl.app.authorization.user.query.UserQueryService;
import pl.app.authorization.user.query.dto.UserDto;
import pl.app.common.cqrs.command.gateway.CommandGateway;
import pl.app.common.util.EntityLocationUriUtils;

import java.util.UUID;

@RestController
@RequestMapping(UserCommandController.resourcePath)
@RequiredArgsConstructor
public class UserCommandController {
    public static final String resourceName = "users";
    public static final String resourcePath = "/api/v1/" + resourceName + "/commands";

    private final CommandGateway gateway;
    public final UserQueryService service;

    @PostMapping("/register")
    public ResponseEntity<UserDto> handle(@RequestBody RegisterUserCommand command, HttpServletRequest request) {
        UUID groupId = gateway.send(command);
        return ResponseEntity
                .created(EntityLocationUriUtils.createdEntityLocationURI(groupId, request.getRequestURI()))
                .body(service.fetchById(groupId, UserDto.class));
    }

    @PostMapping("/add-role")
    public ResponseEntity<Void> handle(@RequestBody AddRoleToUserCommand command) {
        gateway.sendAsync(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    @PostMapping("/remove-role")
    public ResponseEntity<Void> handle(@RequestBody RemoveRoleFromUserCommand command) {
        gateway.sendAsync(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    @PostMapping("/change-password")
    public ResponseEntity<Void> handle(@RequestBody ChangePasswordCommand command) {
        gateway.sendAsync(command);
        return ResponseEntity
                .accepted()
                .build();
    }
}
