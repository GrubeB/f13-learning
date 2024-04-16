package pl.app.learning.topic.adapter.in;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.cqrs.command.gateway.CommandGateway;
import pl.app.common.util.EntityLocationUriUtils;
import pl.app.learning.topic.application.port.in.command.*;
import pl.app.learning.topic.query.TopicQueryService;
import pl.app.learning.topic.query.dto.TopicDto;

import java.util.UUID;

@RestController
@RequestMapping(TopicCommandController.resourcePath)
@RequiredArgsConstructor
public class TopicCommandController {
    public static final String resourceName = "topics";
    public static final String resourcePath = "/api/v1/" + resourceName + "/commands";

    private final CommandGateway gateway;
    public final TopicQueryService service;

    @PostMapping("/create")
    public ResponseEntity<TopicDto> handle(@RequestBody CreateTopicCommand command, HttpServletRequest request) {
        UUID topicId = gateway.send(command);
        TopicDto topic = service.fetchById(topicId, TopicDto.class);
        return ResponseEntity
                .created(EntityLocationUriUtils.createdEntityLocationURI(topicId, request.getRequestURI()))
                .body(topic);
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> handle(@RequestBody DeleteTopicCommand command) {
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    @PostMapping("/update")
    public ResponseEntity<Void> handle(@RequestBody UpdateTopicCommand command) {
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    @PostMapping("/change-status")
    public ResponseEntity<Void> handle(@RequestBody ChangeTopicStatusCommand command) {
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    // CATEGORY
    @PostMapping("/add-category")
    public ResponseEntity<Void> handle(@RequestBody AddCategoryToTopicCommand command) {
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    @PostMapping("/remove-category")
    public ResponseEntity<Void> handle(@RequestBody RemoveCategoryFromTopicCommand command) {
        gateway.sendAsync(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    // SNAPSHOT
    @PostMapping("/revert-snapshot")
    public ResponseEntity<Void> handle(@RequestBody RevertTopicSnapshotCommand command) {
        gateway.sendAsync(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    // REVISION
    @PostMapping("/merge-revision")
    public ResponseEntity<Void> handle(@RequestBody MergeRevisionToTopicCommand command) {
        gateway.sendAsync(command);
        return ResponseEntity
                .accepted()
                .build();
    }
}
