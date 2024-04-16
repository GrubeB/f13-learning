package pl.app.learning.group.adapter.in;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.app.common.cqrs.command.gateway.CommandGateway;
import pl.app.common.util.EntityLocationUriUtils;
import pl.app.learning.group.application.domain.GroupStatus;
import pl.app.learning.group.application.port.in.command.ChangeGroupStatusCommand;
import pl.app.learning.group.application.port.in.command.CreateGroupCommand;
import pl.app.learning.group.application.port.in.command.DeleteGroupCommand;
import pl.app.learning.group.application.port.in.command.UpdateGroupCommand;
import pl.app.learning.group.query.GroupQueryService;
import pl.app.learning.group.query.dto.GroupDto;

import java.util.UUID;

@RestController
@RequestMapping(GroupController.resourcePath)
@RequiredArgsConstructor
public class GroupController {
    public static final String resourceName = "groups";
    public static final String resourcePath = "/api/v1/" + resourceName;

    private final CommandGateway gateway;
    public final GroupQueryService service;

    @PostMapping
    public ResponseEntity<GroupDto> create(@RequestBody CreateGroupCommand command, HttpServletRequest request) {
        UUID aggregateId = gateway.send(command);
        var dto = service.fetchById(aggregateId, GroupDto.class);
        return ResponseEntity
                .created(EntityLocationUriUtils.createdEntityLocationURI(aggregateId, request.getRequestURI()))
                .body(dto);
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<Void> delete(@PathVariable UUID groupId) {
        var command = new DeleteGroupCommand(groupId);
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    @PutMapping("/{groupId}")
    public ResponseEntity<Void> update(@PathVariable UUID groupId, @RequestBody UpdateGroupCommand command) {
        command.setGroupId(groupId);
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    @PutMapping("/{groupId}/status/{status}")
    public ResponseEntity<Void> changeStatus(@PathVariable UUID groupId, @PathVariable GroupStatus status) {
        var command = new ChangeGroupStatusCommand(groupId, status);
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }
}
