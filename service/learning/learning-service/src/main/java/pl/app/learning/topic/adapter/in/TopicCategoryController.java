package pl.app.learning.topic.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.app.common.cqrs.command.gateway.CommandGateway;
import pl.app.learning.topic.application.port.in.command.AddCategoryToTopicCommand;
import pl.app.learning.topic.application.port.in.command.RemoveCategoryFromTopicCommand;
import pl.app.learning.topic.query.TopicQueryService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(TopicCategoryController.resourcePath)
@RequiredArgsConstructor
public class TopicCategoryController {
    public static final String resourceName = "topics";
    public static final String resourcePath = "/api/v1/" + resourceName;

    private final CommandGateway gateway;
    public final TopicQueryService service;

    public static final String addCategoryPath = "/add-category";

    @PostMapping(path = addCategoryPath)
    public ResponseEntity<Void> handle(@RequestBody AddCategoryToTopicCommand command) {
        gateway.sendAsync(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    public static final String removeCategoryPath = "/remove-category";

    @PostMapping(path = removeCategoryPath)
    public ResponseEntity<Void> handle(@RequestBody RemoveCategoryFromTopicCommand command) {
        gateway.sendAsync(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    @PostMapping(path = "/{topicId}/categories/{categoryId}")
    public ResponseEntity<Void> addCategoryToTopicCommand(@PathVariable UUID topicId, @PathVariable UUID categoryId) {
        gateway.sendAsync(new AddCategoryToTopicCommand(topicId, List.of(categoryId)));
        return ResponseEntity
                .accepted()
                .build();
    }

    @DeleteMapping(path = "/{topicId}/categories/{categoryId}")
    public ResponseEntity<Void> removeCategoryFromTopicCommand(@PathVariable UUID topicId, @PathVariable UUID categoryId) {
        gateway.sendAsync(new RemoveCategoryFromTopicCommand(topicId, List.of(categoryId)));
        return ResponseEntity
                .accepted()
                .build();
    }
}
