package pl.app.learning.group.adapter.in;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.cqrs.command.gateway.CommandGateway;
import pl.app.common.util.EntityLocationUriUtils;
import pl.app.learning.group.application.port.in.command.*;
import pl.app.learning.group.query.GroupQueryService;
import pl.app.learning.group.query.dto.GroupDto;

import java.util.UUID;

@RestController
@RequestMapping(GroupCommandController.resourcePath)
@RequiredArgsConstructor
public class GroupCommandController {
    public static final String resourceName = "groups";
    public static final String resourcePath = "/api/v1/" + resourceName + "/commands";

    private final CommandGateway gateway;
    public final GroupQueryService service;

    @PostMapping("/create")
    public ResponseEntity<GroupDto> handle(@RequestBody CreateGroupCommand command, HttpServletRequest request) {
        UUID groupId = gateway.send(command);
        return ResponseEntity
                .created(EntityLocationUriUtils.createdEntityLocationURI(groupId, request.getRequestURI()))
                .body(service.fetchById(groupId, GroupDto.class));
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> handle(@RequestBody DeleteGroupCommand command) {
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    @PostMapping("/update")
    public ResponseEntity<Void> handle(@RequestBody UpdateGroupCommand command) {
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    @PostMapping("/change-status")
    public ResponseEntity<Void> handle(@RequestBody ChangeGroupStatusCommand command) {
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    // SNAPSHOT
    @PostMapping("/revert-snapshot")
    public ResponseEntity<Void> handle(@RequestBody RevertGroupSnapshotCommand command) {
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    // REVISION
    @PostMapping("/merge-revision")
    public ResponseEntity<Void> handle(@RequestBody MergeRevisionToGroupCommand command) {
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }
}
