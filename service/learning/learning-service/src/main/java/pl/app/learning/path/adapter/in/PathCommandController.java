package pl.app.learning.path.adapter.in;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.cqrs.command.gateway.CommandGateway;
import pl.app.common.util.EntityLocationUriUtils;
import pl.app.learning.path.application.port.in.command.ChangePathStatusCommand;
import pl.app.learning.path.application.port.in.command.CreatePathCommand;
import pl.app.learning.path.application.port.in.command.DeletePathCommand;
import pl.app.learning.path.application.port.in.command.UpdatePathCommand;
import pl.app.learning.path.query.PathQueryService;
import pl.app.learning.path.query.dto.PathDto;

import java.util.UUID;

@RestController
@RequestMapping(PathCommandController.resourcePath)
@RequiredArgsConstructor
public class PathCommandController {
    public static final String resourceName = "paths";
    public static final String resourcePath = "/api/v1/" + resourceName + "/commands";

    private final CommandGateway gateway;
    public final PathQueryService service;

    @PostMapping("/create")
    public ResponseEntity<PathDto> handle(@RequestBody CreatePathCommand command, HttpServletRequest request) {
        UUID aggregateId = gateway.send(command);
        return ResponseEntity
                .created(EntityLocationUriUtils.createdEntityLocationURI(aggregateId, request.getRequestURI()))
                .body(service.fetchById(aggregateId, PathDto.class));
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> handle(@RequestBody DeletePathCommand command) {
        gateway.sendAsync(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    @PostMapping("/update")
    public ResponseEntity<Void> handle(@RequestBody UpdatePathCommand command) {
        gateway.sendAsync(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    @PostMapping("/change-status")
    public ResponseEntity<Void> handle(@RequestBody ChangePathStatusCommand command) {
        gateway.sendAsync(command);
        return ResponseEntity
                .accepted()
                .build();
    }
}
