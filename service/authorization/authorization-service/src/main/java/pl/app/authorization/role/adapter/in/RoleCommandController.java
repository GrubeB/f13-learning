package pl.app.authorization.role.adapter.in;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.authorization.role.application.port.in.*;
import pl.app.authorization.role.query.RoleQueryService;
import pl.app.authorization.role.query.dto.RoleDto;
import pl.app.common.cqrs.command.gateway.CommandGateway;
import pl.app.common.util.EntityLocationUriUtils;

import java.util.UUID;

@RestController
@RequestMapping(RoleCommandController.resourcePath)
@RequiredArgsConstructor
public class RoleCommandController {
    public static final String resourceName = "roles";
    public static final String resourcePath = "/api/v1/" + resourceName + "/commands";

    private final CommandGateway gateway;
    public final RoleQueryService service;

    @PostMapping("/create")
    public ResponseEntity<RoleDto> handle(@RequestBody CreateRoleCommand command, HttpServletRequest request) {
        UUID aggregateId = gateway.send(command);
        return ResponseEntity
                .created(EntityLocationUriUtils.createdEntityLocationURI(aggregateId, request.getRequestURI()))
                .body(service.fetchById(aggregateId, RoleDto.class));
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> handle(@RequestBody DeleteRoleCommand command) {
        gateway.sendAsync(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    @PostMapping("/update")
    public ResponseEntity<Void> handle(@RequestBody UpdateRoleCommand command) {
        gateway.sendAsync(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    @PostMapping("/add-permission")
    public ResponseEntity<Void> handle(@RequestBody AddPermissionToRoleCommand command) {
        gateway.sendAsync(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    @PostMapping("/remove-permission")
    public ResponseEntity<Void> handle(@RequestBody RemovePermissionFromRoleCommand command) {
        gateway.sendAsync(command);
        return ResponseEntity
                .accepted()
                .build();
    }
}
