package pl.app.learning.group.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.cqrs.command.gateway.CommandGateway;
import pl.app.learning.group.application.port.in.command.MergeRevisionToGroupCommand;

import java.util.UUID;

@RestController("pl.app.learning.group.adapter.in.GroupRevisionController")
@RequestMapping(GroupRevisionController.resourcePath)
@RequiredArgsConstructor
public class GroupRevisionController {
    public static final String resourceName = "group-revisions";
    public static final String resourcePath = "/api/v1/groups/{groupId}/" + resourceName;

    private final CommandGateway gateway;

    @PostMapping("/{revisionId}/merge")
    public ResponseEntity<Void> handle(@PathVariable UUID groupId, @PathVariable UUID revisionId) {
        var command = new MergeRevisionToGroupCommand(groupId, revisionId);
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }
}
