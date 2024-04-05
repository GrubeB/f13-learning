package pl.app.learning.topic.adapter.in;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.app.common.cqrs.command.gateway.CommandGateway;
import pl.app.common.util.EntityLocationUriUtils;
import pl.app.learning.topic.application.domain.TopicStatus;
import pl.app.learning.topic.application.port.in.command.ChangeTopicStatusCommand;
import pl.app.learning.topic.application.port.in.command.CreateTopicCommand;
import pl.app.learning.topic.application.port.in.command.DeleteTopicCommand;
import pl.app.learning.topic.application.port.in.command.UpdateTopicCommand;
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
    public ResponseEntity<TopicDto> create(@RequestBody CreateTopicCommand command, HttpServletRequest request) {
        UUID topicId = gateway.send(command);
        TopicDto topic = service.fetchById(topicId, TopicDto.class);
        return ResponseEntity
                .created(EntityLocationUriUtils.createdEntityLocationURI(topicId, request.getRequestURI()))
                .body(topic);
    }

    @DeleteMapping("/{topicId}")
    public ResponseEntity<Void> delete(@PathVariable UUID topicId) {
        var command = new DeleteTopicCommand(topicId);
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    @PutMapping("/{topicId}")
    public ResponseEntity<Void> update(@PathVariable UUID topicId, @RequestBody UpdateTopicCommand command) {
        command.setTopicId(topicId);
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    @PutMapping("/{topicId}/status/{status}")
    public ResponseEntity<Void> changeStatus(@PathVariable UUID topicId, @PathVariable TopicStatus status) {
        var command = new ChangeTopicStatusCommand(topicId, status);
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }
}
