package pl.app.learning.group_revision.adapter.in;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.cqrs.command.gateway.CommandGateway;
import pl.app.common.util.EntityLocationUriUtils;
import pl.app.learning.group_revision.application.port.in.command.CreateGroupRevisionCommand;
import pl.app.learning.group_revision.application.port.in.command.DeleteGroupRevisionCommand;
import pl.app.learning.group_revision.application.port.in.command.UpdateGroupRevisionCommand;
import pl.app.learning.group_revision.query.GroupRevisionQueryService;
import pl.app.learning.group_revision.query.dto.GroupRevisionDto;

import java.util.UUID;

@RestController
@RequestMapping(GroupRevisionCommandController.resourcePath)
@RequiredArgsConstructor
public class GroupRevisionCommandController {
    public static final String resourceName = "group-revisions";
    public static final String resourcePath = "/api/v1/" + resourceName + "/commands";

    private final CommandGateway gateway;
    public final GroupRevisionQueryService service;

    @PostMapping("/create")
    public ResponseEntity<GroupRevisionDto> handle(@RequestBody CreateGroupRevisionCommand command, HttpServletRequest request) {
        UUID aggregateId = gateway.send(command);
        GroupRevisionDto dto = service.fetchById(aggregateId, GroupRevisionDto.class);
        return ResponseEntity
                .created(EntityLocationUriUtils.createdEntityLocationURI(aggregateId, request.getRequestURI()))
                .body(dto);
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> handle(@RequestBody DeleteGroupRevisionCommand command) {
        gateway.sendAsync(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    @PostMapping("/update")
    public ResponseEntity<Void> handle(@RequestBody UpdateGroupRevisionCommand command) {
        gateway.sendAsync(command);
        return ResponseEntity
                .accepted()
                .build();
    }
}
