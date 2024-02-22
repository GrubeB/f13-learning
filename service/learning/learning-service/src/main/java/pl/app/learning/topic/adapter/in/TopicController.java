package pl.app.learning.topic.adapter.in;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.app.common.cqrs.command.gateway.CommandGateway;
import pl.app.common.util.EntityLocationUriUtils;
import pl.app.learning.topic.application.port.in.command.ChangeTopicStatusCommand;
import pl.app.learning.topic.application.port.in.command.CreateTopicCommand;
import pl.app.learning.topic.application.port.in.command.DeleteTopicCommand;
import pl.app.learning.topic.query.TopicQueryService;
import pl.app.learning.topic.query.dto.TopicDto;

import java.util.UUID;

@RestController
@RequestMapping(TopicController.resourcePath)
@RequiredArgsConstructor
public class TopicController {
    public static final String resourceName = "topics";
    public static final String resourcePath = "/api/v1/" + resourceName;

    private final CommandGateway gateway;
    public final TopicQueryService service;

    @PostMapping
    public ResponseEntity<TopicDto> createAccommodationType(@RequestBody CreateTopicCommand command, HttpServletRequest request) {
        UUID topicId = gateway.send(command);
        TopicDto topic = service.fetchById(topicId, TopicDto.class);
        return ResponseEntity
                .created(EntityLocationUriUtils.createdEntityLocationURI(topicId, request.getRequestURI()))
                .body(topic);
    }

    @DeleteMapping(path = {"/{topicId}", ""})
    public ResponseEntity<Void> handle(@RequestBody DeleteTopicCommand command) {
        gateway.sendAsync(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    public static final String changeStatusPath = "/change-status";

    @PostMapping(path = changeStatusPath)
    public ResponseEntity<Void> handle(@RequestBody ChangeTopicStatusCommand command) {
        gateway.sendAsync(command);
        return ResponseEntity
                .accepted()
                .build();
    }
    @PutMapping(path = "/{topicId}/status")
    public ResponseEntity<Void> handle(@PathVariable UUID topicId, @RequestBody ChangeTopicStatusCommand command) {
        gateway.sendAsync(command);
        return ResponseEntity
                .accepted()
                .build();
    }
}
