package pl.app.learning.topic.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.cqrs.command.gateway.CommandGateway;
import pl.app.learning.topic.application.port.in.command.MergeRevisionToTopicCommand;

import java.util.UUID;

@RestController
@RequestMapping(TopicRevisionController.resourcePath)
@RequiredArgsConstructor
public class TopicRevisionController {
    public static final String resourceName = "topic-revisions";
    public static final String resourcePath = "/api/v1/topics/{topicId}/" + resourceName;

    private final CommandGateway gateway;

    @PostMapping("/{topicRevisionId}/merge")
    public ResponseEntity<Void> handle(@PathVariable UUID topicId, @PathVariable UUID topicRevisionId) {
        var command = new MergeRevisionToTopicCommand(topicId, topicRevisionId);
        gateway.sendAsync(command);
        return ResponseEntity
                .accepted()
                .build();
    }
}
