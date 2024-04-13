package pl.app.learning.group.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.cqrs.command.gateway.CommandGateway;
import pl.app.learning.group.application.port.in.command.RevertGroupSnapshotCommand;

import java.util.UUID;

@RestController
@RequestMapping(GroupSnapshotController.resourcePath)
@RequiredArgsConstructor
public class GroupSnapshotController {
    public static final String resourceName = "snapshots";
    public static final String resourcePath = "/api/v1/groups/{groupId}/" + resourceName;

    private final CommandGateway gateway;

    @PostMapping(path = "/{snapshotNumber}/revert")
    public ResponseEntity<Void> revertSnapshot(@PathVariable UUID groupId, @PathVariable Long snapshotNumber) {
        gateway.send(new RevertGroupSnapshotCommand(groupId, snapshotNumber));
        return ResponseEntity
                .accepted()
                .build();
    }
}
