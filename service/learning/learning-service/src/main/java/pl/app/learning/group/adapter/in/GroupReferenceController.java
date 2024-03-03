package pl.app.learning.group.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.app.common.cqrs.command.gateway.CommandGateway;
import pl.app.learning.group.application.port.in.command.AddReferenceToGroupCommand;
import pl.app.learning.group.application.port.in.command.RemoveReferenceFromGroupCommand;

import java.util.UUID;

@RestController
@RequestMapping(GroupReferenceController.resourcePath)
@RequiredArgsConstructor
public class GroupReferenceController {
    public static final String resourceName = "references";
    public static final String resourcePath = "/api/v1/groups/{groupId}/" + resourceName;

    private final CommandGateway gateway;


    @PostMapping("/{referenceId}")
    public ResponseEntity<Void> add(@PathVariable UUID groupId, @PathVariable UUID referenceId) {
        gateway.sendAsync(new AddReferenceToGroupCommand(groupId, referenceId));
        return ResponseEntity
                .accepted()
                .build();
    }

    @DeleteMapping("/{referenceId}")
    public ResponseEntity<Void> remove(@PathVariable UUID groupId, @PathVariable UUID referenceId) {
        gateway.sendAsync(new RemoveReferenceFromGroupCommand(groupId, referenceId));
        return ResponseEntity
                .accepted()
                .build();
    }
}
