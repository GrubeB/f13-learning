package pl.app.authorization.permision.adapter.in;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.authorization.permision.application.port.in.command.CreatePermissionCommand;
import pl.app.authorization.permision.application.port.in.command.DeletePermissionCommand;
import pl.app.authorization.permision.query.PermissionQueryService;
import pl.app.authorization.permision.query.dto.PermissionDto;
import pl.app.common.cqrs.command.gateway.CommandGateway;
import pl.app.common.util.EntityLocationUriUtils;

import java.util.UUID;

@RestController
@RequestMapping(PermissionCommandController.resourcePath)
@RequiredArgsConstructor
public class PermissionCommandController {
    public static final String resourceName = "permissions";
    public static final String resourcePath = "/api/v1/" + resourceName + "/commands";

    private final CommandGateway gateway;
    public final PermissionQueryService service;

    @PostMapping("/create")
    public ResponseEntity<PermissionDto> handle(@RequestBody CreatePermissionCommand command, HttpServletRequest request) {
        UUID aggregateId = gateway.send(command);
        return ResponseEntity
                .created(EntityLocationUriUtils.createdEntityLocationURI(aggregateId, request.getRequestURI()))
                .body(service.fetchById(aggregateId, PermissionDto.class));
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> handle(@RequestBody DeletePermissionCommand command) {
        gateway.sendAsync(command);
        return ResponseEntity
                .accepted()
                .build();
    }
}
