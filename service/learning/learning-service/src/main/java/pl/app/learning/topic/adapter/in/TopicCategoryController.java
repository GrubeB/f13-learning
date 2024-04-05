package pl.app.learning.topic.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.app.common.cqrs.command.gateway.CommandGateway;
import pl.app.learning.topic.application.port.in.command.AddCategoryToTopicCommand;
import pl.app.learning.topic.application.port.in.command.RemoveCategoryFromTopicCommand;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(TopicCategoryController.resourcePath)
@RequiredArgsConstructor
public class TopicCategoryController {
    public static final String resourceName = "categories";
    public static final String resourcePath = "/api/v1/topics/{topicId}/" + resourceName;

    private final CommandGateway gateway;

    @PostMapping("/{categoryId}")
    public ResponseEntity<Void> create(@PathVariable UUID topicId, @PathVariable UUID categoryId) {
        gateway.send(new AddCategoryToTopicCommand(topicId, List.of(categoryId)));
        return ResponseEntity
                .accepted()
                .build();
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> delete(@PathVariable UUID topicId, @PathVariable UUID categoryId) {
        gateway.send(new RemoveCategoryFromTopicCommand(topicId, List.of(categoryId)));
        return ResponseEntity
                .accepted()
                .build();
    }
}
