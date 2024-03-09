package pl.app.authorization.user.adapter.in;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.authorization.user.application.port.in.RegisterUserCommand;
import pl.app.authorization.user.query.UserQueryService;
import pl.app.authorization.user.query.dto.UserDto;
import pl.app.common.cqrs.command.gateway.CommandGateway;
import pl.app.common.util.EntityLocationUriUtils;

import java.util.UUID;

@RestController
@RequestMapping(UserController.resourcePath)
@RequiredArgsConstructor
public class UserController {
    public static final String resourceName = "users";
    public static final String resourcePath = "/api/v1/" + resourceName;

    private final CommandGateway gateway;
    public final UserQueryService service;

    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody RegisterUserCommand command, HttpServletRequest request) {
        UUID aggregateId = gateway.send(command);
        var dto = service.fetchById(aggregateId, UserDto.class);
        return ResponseEntity
                .created(EntityLocationUriUtils.createdEntityLocationURI(aggregateId, request.getRequestURI()))
                .body(dto);
    }
}
