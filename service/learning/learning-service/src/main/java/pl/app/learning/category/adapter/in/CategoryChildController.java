package pl.app.learning.category.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.app.common.cqrs.command.gateway.CommandGateway;
import pl.app.learning.category.application.port.in.command.*;
import pl.app.learning.category.query.CategoryQueryService;
import pl.app.learning.topic.application.port.in.command.AddCategoryToTopicCommand;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(CategoryChildController.resourcePath)
@RequiredArgsConstructor
public class CategoryChildController {
    public static final String resourceName = "categories";
    public static final String resourcePath = "/api/v1/" + resourceName;

    private final CommandGateway gateway;

    @PostMapping(path = "/{categoryId}/children/{childId}")
    public ResponseEntity<Void> addChildCategoryCommand(@PathVariable UUID categoryId, @PathVariable UUID childId) {
        gateway.sendAsync(new AddChildCategoryCommand(categoryId, childId));
        return ResponseEntity
                .accepted()
                .build();
    }
    @DeleteMapping(path = "/{categoryId}/children/{childId}")
    public ResponseEntity<Void> removeChildCategoryCommand(@PathVariable UUID categoryId, @PathVariable UUID childId) {
        gateway.sendAsync(new RemoveChildCategoryCommand(categoryId, childId));
        return ResponseEntity
                .accepted()
                .build();
    }
}
