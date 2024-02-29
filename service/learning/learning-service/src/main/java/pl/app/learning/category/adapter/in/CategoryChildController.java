package pl.app.learning.category.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.app.common.cqrs.command.gateway.CommandGateway;
import pl.app.learning.category.application.port.in.command.AddChildCategoryCommand;
import pl.app.learning.category.application.port.in.command.RemoveChildCategoryCommand;

import java.util.UUID;

@RestController
@RequestMapping(CategoryChildController.resourcePath)
@RequiredArgsConstructor
public class CategoryChildController {
    public static final String resourceName = "children";
    public static final String resourcePath = "/api/v1/categories/{categoryId}/" + resourceName;

    private final CommandGateway gateway;

    @PostMapping(path = "/{childId}")
    public ResponseEntity<Void> addChildCategoryCommand(@PathVariable UUID categoryId, @PathVariable UUID childId) {
        gateway.sendAsync(new AddChildCategoryCommand(categoryId, childId));
        return ResponseEntity
                .accepted()
                .build();
    }

    @DeleteMapping(path = "/{childId}")
    public ResponseEntity<Void> removeChildCategoryCommand(@PathVariable UUID categoryId, @PathVariable UUID childId) {
        gateway.sendAsync(new RemoveChildCategoryCommand(categoryId, childId));
        return ResponseEntity
                .accepted()
                .build();
    }
}
