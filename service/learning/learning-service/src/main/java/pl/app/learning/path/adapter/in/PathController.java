package pl.app.learning.path.adapter.in;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.app.common.cqrs.command.gateway.CommandGateway;
import pl.app.common.util.EntityLocationUriUtils;
import pl.app.learning.path.application.domain.PathStatus;
import pl.app.learning.path.application.port.in.command.ChangePathStatusCommand;
import pl.app.learning.path.application.port.in.command.CreatePathCommand;
import pl.app.learning.path.application.port.in.command.DeletePathCommand;
import pl.app.learning.path.application.port.in.command.UpdatePathCommand;
import pl.app.learning.path.query.PathQueryService;
import pl.app.learning.path.query.dto.PathDto;

import java.util.UUID;

@RestController
@RequestMapping(PathController.resourcePath)
@RequiredArgsConstructor
public class PathController {
    public static final String resourceName = "paths";
    public static final String resourcePath = "/api/v1/" + resourceName;

    private final CommandGateway gateway;
    public final PathQueryService service;

    @PostMapping
    public ResponseEntity<PathDto> create(@RequestBody CreatePathCommand command, HttpServletRequest request) {
        UUID aggregateId = gateway.send(command);
        var dto = service.fetchById(aggregateId, PathDto.class);
        return ResponseEntity
                .created(EntityLocationUriUtils.createdEntityLocationURI(aggregateId, request.getRequestURI()))
                .body(dto);
    }

    @DeleteMapping("/{pathId}")
    public ResponseEntity<Void> delete(@PathVariable UUID pathId) {
        var command = new DeletePathCommand(pathId);
        gateway.sendAsync(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    @PutMapping("/{pathId}")
    public ResponseEntity<Void> update(@PathVariable UUID pathId, @RequestBody UpdatePathCommand command) {
        command.setPathId(pathId);
        gateway.sendAsync(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    @PutMapping("/{pathId}/status/{status}")
    public ResponseEntity<Void> changeStatus(@PathVariable UUID pathId, @PathVariable PathStatus status) {
        var command = new ChangePathStatusCommand(pathId, status);
        gateway.sendAsync(command);
        return ResponseEntity
                .accepted()
                .build();
    }
}
