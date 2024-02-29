package pl.app.learning.topic.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.app.common.cqrs.command.gateway.CommandGateway;
import pl.app.learning.topic.application.port.in.command.AddReferenceToTopicCommand;
import pl.app.learning.topic.application.port.in.command.RemoveReferenceFromTopicCommand;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(TopicReferenceController.resourcePath)
@RequiredArgsConstructor
public class TopicReferenceController {
    public static final String resourceName = "references";
    public static final String resourcePath = "/api/v1/topics/{topicId}/" + resourceName;

    private final CommandGateway gateway;


    @PostMapping("/{referenceId}")
    public ResponseEntity<Void> addReferenceToTopicCommand(@PathVariable UUID topicId, @PathVariable UUID referenceId) {
        gateway.sendAsync(new AddReferenceToTopicCommand(topicId, List.of(referenceId)));
        return ResponseEntity
                .accepted()
                .build();
    }

    @DeleteMapping("/{referenceId}")
    public ResponseEntity<Void> removeCategoryFromTopicCommand(@PathVariable UUID topicId, @PathVariable UUID referenceId) {
        gateway.sendAsync(new RemoveReferenceFromTopicCommand(topicId, List.of(referenceId)));
        return ResponseEntity
                .accepted()
                .build();
    }
}
