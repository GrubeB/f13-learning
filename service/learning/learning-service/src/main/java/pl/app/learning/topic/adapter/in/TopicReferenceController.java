package pl.app.learning.topic.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.app.common.cqrs.command.gateway.CommandGateway;
import pl.app.learning.topic.application.port.in.command.AddReferenceToTopicCommand;
import pl.app.learning.topic.application.port.in.command.RemoveReferenceFromTopicCommand;
import pl.app.learning.topic.query.TopicQueryService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(TopicReferenceController.resourcePath)
@RequiredArgsConstructor
public class TopicReferenceController {
    public static final String resourceName = "topics";
    public static final String resourcePath = "/api/v1/" + resourceName;

    private final CommandGateway gateway;
    public final TopicQueryService service;

    public static final String addReferencePath = "/add-reference";

    @PostMapping(path = addReferencePath)
    public ResponseEntity<Void> handle(@RequestBody AddReferenceToTopicCommand command) {
        gateway.sendAsync(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    public static final String removeReferencePath = "/remove-reference";

    @PostMapping(path = removeReferencePath)
    public ResponseEntity<Void> handle(@RequestBody RemoveReferenceFromTopicCommand command) {
        gateway.sendAsync(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    @PostMapping(path = "/{topicId}/references/{referenceId}")
    public ResponseEntity<Void> addReferenceToTopicCommand(@PathVariable UUID topicId, @PathVariable UUID referenceId) {
        gateway.sendAsync(new AddReferenceToTopicCommand(topicId, List.of(referenceId)));
        return ResponseEntity
                .accepted()
                .build();
    }

    @DeleteMapping(path = "/{topicId}/references/{referenceId}")
    public ResponseEntity<Void> removeCategoryFromTopicCommand(@PathVariable UUID topicId, @PathVariable UUID referenceId) {
        gateway.sendAsync(new RemoveReferenceFromTopicCommand(topicId, List.of(referenceId)));
        return ResponseEntity
                .accepted()
                .build();
    }
}
