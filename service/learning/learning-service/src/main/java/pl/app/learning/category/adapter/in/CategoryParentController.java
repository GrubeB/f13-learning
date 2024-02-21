package pl.app.learning.category.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.app.common.cqrs.command.gateway.CommandGateway;
import pl.app.learning.category.application.port.in.command.AddParentCategoryCommand;
import pl.app.learning.category.application.port.in.command.RemoveParentCategoryCommand;

import java.util.UUID;

@RestController
@RequestMapping(CategoryParentController.resourcePath)
@RequiredArgsConstructor
public class CategoryParentController {
    public static final String resourceName = "categories";
    public static final String resourcePath = "/api/v1/" + resourceName;

    private final CommandGateway gateway;

    public static final String addParentCategoryPath = "/add-parent";

    @PostMapping(path = addParentCategoryPath)
    public ResponseEntity<Void> handle(@RequestBody AddParentCategoryCommand command) {
        gateway.sendAsync(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    public static final String removeParentCategoryPath = "/remove-parent";

    @PostMapping(path = removeParentCategoryPath)
    public ResponseEntity<Void> handle(@RequestBody RemoveParentCategoryCommand command) {
        gateway.sendAsync(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    @PostMapping(path = "/{categoryId}/parents/{parentId}")
    public ResponseEntity<Void> addParentCategoryCommand(@PathVariable UUID categoryId, @PathVariable UUID parentId) {
        gateway.sendAsync(new AddParentCategoryCommand(categoryId, parentId));
        return ResponseEntity
                .accepted()
                .build();
    }

    @DeleteMapping(path = "/{categoryId}/parents/{parentId}")
    public ResponseEntity<Void> removeParentCategoryCommand(@PathVariable UUID categoryId, @PathVariable UUID parentId) {
        gateway.sendAsync(new RemoveParentCategoryCommand(categoryId, parentId));
        return ResponseEntity
                .accepted()
                .build();
    }
}
