package pl.app.authorization.role.adapter.in;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.app.authorization.permision.query.PermissionQueryService;
import pl.app.authorization.role.application.port.in.CreateRoleCommand;
import pl.app.authorization.role.application.port.in.DeleteRoleCommand;
import pl.app.authorization.role.application.port.in.UpdateRoleCommand;
import pl.app.authorization.role.query.dto.RoleDto;
import pl.app.common.cqrs.command.gateway.CommandGateway;
import pl.app.common.util.EntityLocationUriUtils;

import java.util.UUID;

@RestController
@RequestMapping(RoleController.resourcePath)
@RequiredArgsConstructor
public class RoleController {
    public static final String resourceName = "roles";
    public static final String resourcePath = "/api/v1/" + resourceName;

    private final CommandGateway gateway;
    public final PermissionQueryService service;

    @PostMapping
    public ResponseEntity<RoleDto> create(@RequestBody CreateRoleCommand command, HttpServletRequest request) {
        UUID aggregateId = gateway.send(command);
        var dto = service.fetchById(aggregateId, RoleDto.class);
        return ResponseEntity
                .created(EntityLocationUriUtils.createdEntityLocationURI(aggregateId, request.getRequestURI()))
                .body(dto);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> delete(@PathVariable String name) {
        var command = new DeleteRoleCommand(name);
        gateway.sendAsync(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    @PutMapping("/{name}")
    public ResponseEntity<Void> update(@PathVariable String name, @RequestBody UpdateRoleCommand command) {
        command.setName(name);
        gateway.sendAsync(command);
        return ResponseEntity
                .accepted()
                .build();
    }
}
