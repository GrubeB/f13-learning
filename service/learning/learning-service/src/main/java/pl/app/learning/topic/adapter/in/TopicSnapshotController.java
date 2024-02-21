package pl.app.learning.topic.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.app.common.cqrs.command.gateway.CommandGateway;
import pl.app.learning.topic.application.port.in.command.RevertTopicSnapshotCommand;

import java.util.UUID;

@RestController
@RequestMapping(TopicSnapshotController.resourcePath)
@RequiredArgsConstructor
public class TopicSnapshotController {
    public static final String resourceName = "topics";
    public static final String resourcePath = "/api/v1/" + resourceName;

    private final CommandGateway gateway;
    public static final String revertSnapshotPath = "/revert-snapshot";
    @PostMapping(path = revertSnapshotPath)
    public ResponseEntity<Void> handle(@RequestBody RevertTopicSnapshotCommand command) {
        gateway.sendAsync(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    @PostMapping(path = "/{topicId}/snapshots/{snapshotNumber}")
    public ResponseEntity<Void> removeCategoryFromTopicCommand(@PathVariable UUID topicId, @PathVariable Long snapshotNumber) {
        gateway.sendAsync(new RevertTopicSnapshotCommand(topicId, snapshotNumber));
        return ResponseEntity
                .accepted()
                .build();
    }
}
