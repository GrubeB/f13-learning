package pl.app.authorization.permision.adapter.in;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.app.authorization.permision.application.port.in.command.CreatePermissionCommand;
import pl.app.authorization.permision.application.port.in.command.DeletePermissionCommand;
import pl.app.authorization.permision.query.PermissionQueryService;
import pl.app.authorization.permision.query.dto.PermissionDto;
import pl.app.common.cqrs.command.gateway.CommandGateway;
import pl.app.common.util.EntityLocationUriUtils;

import java.util.UUID;

@RestController
@RequestMapping(PermissionController.resourcePath)
@RequiredArgsConstructor
public class PermissionController {
    public static final String resourceName = "permissions";
    public static final String resourcePath = "/api/v1/" + resourceName;

    private final CommandGateway gateway;
    public final PermissionQueryService service;

    @PostMapping
    public ResponseEntity<PermissionDto> create(@RequestBody CreatePermissionCommand command, HttpServletRequest request) {
        UUID aggregateId = gateway.send(command);
        var dto = service.fetchById(aggregateId, PermissionDto.class);
        return ResponseEntity
                .created(EntityLocationUriUtils.createdEntityLocationURI(aggregateId, request.getRequestURI()))
                .body(dto);
    }

    @DeleteMapping("/{permissionName}")
    public ResponseEntity<Void> delete(@PathVariable String permissionName) {
        var command = new DeletePermissionCommand(permissionName);
        gateway.sendAsync(command);
        return ResponseEntity
                .accepted()
                .build();
    }
}
