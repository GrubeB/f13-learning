package pl.app.learning.topic_revision.adapter.in;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.app.common.cqrs.command.gateway.CommandGateway;
import pl.app.common.util.EntityLocationUriUtils;
import pl.app.learning.topic_revision.application.port.in.command.CreateTopicRevisionCommand;
import pl.app.learning.topic_revision.application.port.in.command.DeleteTopicRevisionCommand;
import pl.app.learning.topic_revision.application.port.in.command.UpdateTopicRevisionCommand;
import pl.app.learning.topic_revision.query.TopicRevisionQueryService;
import pl.app.learning.topic_revision.query.dto.TopicRevisionDto;

import java.util.UUID;

@RestController
@RequestMapping(TopicRevisionController.resourcePath)
@RequiredArgsConstructor
public class TopicRevisionController {
    public static final String resourceName = "topic-revisions";
    public static final String resourcePath = "/api/v1/" + resourceName;

    private final CommandGateway gateway;
    public final TopicRevisionQueryService service;

    @PostMapping
    public ResponseEntity<TopicRevisionDto> createAccommodationType(@RequestBody CreateTopicRevisionCommand command, HttpServletRequest request) {
        UUID topicRevisionId = gateway.send(command);
        TopicRevisionDto dto = service.fetchById(topicRevisionId, TopicRevisionDto.class);
        return ResponseEntity
                .created(EntityLocationUriUtils.createdEntityLocationURI(topicRevisionId, request.getRequestURI()))
                .body(dto);
    }

    @DeleteMapping(path = "/{topicRevisionId}")
    public ResponseEntity<Void> handle(@RequestBody DeleteTopicRevisionCommand command) {
        gateway.sendAsync(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    @PutMapping(path = "/{topicRevisionId}")
    public ResponseEntity<Void> handle(@RequestBody UpdateTopicRevisionCommand command) {
        gateway.sendAsync(command);
        return ResponseEntity
                .accepted()
                .build();
    }
}
