package pl.app.learning.group_revision.adapter.in;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.app.common.cqrs.command.gateway.CommandGateway;
import pl.app.common.util.EntityLocationUriUtils;
import pl.app.learning.group_revision.application.port.in.command.CreateGroupRevisionCommand;
import pl.app.learning.group_revision.application.port.in.command.DeleteGroupRevisionCommand;
import pl.app.learning.group_revision.application.port.in.command.UpdateGroupRevisionCommand;
import pl.app.learning.group_revision.query.GroupRevisionQueryService;
import pl.app.learning.group_revision.query.dto.GroupRevisionDto;

import java.util.UUID;

@RestController
@RequestMapping(GroupRevisionController.resourcePath)
@RequiredArgsConstructor
public class GroupRevisionController {
    public static final String resourceName = "group-revisions";
    public static final String resourcePath = "/api/v1/" + resourceName;

    private final CommandGateway gateway;
    public final GroupRevisionQueryService service;

    @PostMapping
    public ResponseEntity<GroupRevisionDto> create(@RequestBody CreateGroupRevisionCommand command, HttpServletRequest request) {
        UUID revisionId = gateway.send(command);
        GroupRevisionDto dto = service.fetchById(revisionId, GroupRevisionDto.class);
        return ResponseEntity
                .created(EntityLocationUriUtils.createdEntityLocationURI(revisionId, request.getRequestURI()))
                .body(dto);
    }

    @DeleteMapping("/{revisionId}")
    public ResponseEntity<Void> delete(@PathVariable UUID revisionId) {
        var command = new DeleteGroupRevisionCommand(revisionId);
        gateway.sendAsync(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    @PutMapping("/{revisionId}")
    public ResponseEntity<Void> update(@PathVariable UUID revisionId, @RequestBody UpdateGroupRevisionCommand command) {
        command.setRevisionId(revisionId);
        gateway.sendAsync(command);
        return ResponseEntity
                .accepted()
                .build();
    }
}
