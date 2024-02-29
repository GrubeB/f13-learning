package pl.app.learning.topic.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.cqrs.command.gateway.CommandGateway;
import pl.app.learning.topic.application.port.in.command.RevertTopicSnapshotCommand;

import java.util.UUID;

@RestController
@RequestMapping(TopicSnapshotController.resourcePath)
@RequiredArgsConstructor
public class TopicSnapshotController {
    public static final String resourceName = "snapshots";
    public static final String resourcePath = "/api/v1/topics/{topicId}/" + resourceName;

    private final CommandGateway gateway;

    @PostMapping(path = "/{snapshotNumber}/revert")
    public ResponseEntity<Void> revertSnapshot(@PathVariable UUID topicId, @PathVariable Long snapshotNumber) {
        gateway.sendAsync(new RevertTopicSnapshotCommand(topicId, snapshotNumber));
        return ResponseEntity
                .accepted()
                .build();
    }
}
