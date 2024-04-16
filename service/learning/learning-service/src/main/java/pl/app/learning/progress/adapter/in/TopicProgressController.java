package pl.app.learning.progress.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.cqrs.command.gateway.CommandGateway;
import pl.app.learning.progress.application.domain.ProgressType;
import pl.app.learning.progress.application.port.in.SetUserProgressCommand;
import pl.app.learning.voting.application.domain.DomainObjectType;

import java.util.UUID;

@RestController
@RequestMapping(TopicProgressController.resourcePath)
@RequiredArgsConstructor
public class TopicProgressController {
    public static final String resourceName = "topics";
    public static final String resourcePath = "/api/v1/" + resourceName;

    private final CommandGateway gateway;

    @PutMapping("/{topicId}/progresses/{userId}/types/{type}")
    public ResponseEntity<Void> set(@PathVariable UUID topicId, @PathVariable UUID userId, @PathVariable ProgressType type) {
        var command = new SetUserProgressCommand(topicId, DomainObjectType.TOPIC, userId, type);
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }
}
