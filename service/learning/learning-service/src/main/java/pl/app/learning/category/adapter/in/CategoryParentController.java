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
    public static final String resourceName = "parents";
    public static final String resourcePath = "/api/v1/categories/{categoryId}/" + resourceName;

    private final CommandGateway gateway;

    @PostMapping(path = "/{parentId}")
    public ResponseEntity<Void> addParentCategoryCommand(@PathVariable UUID categoryId, @PathVariable UUID parentId) {
        gateway.sendAsync(new AddParentCategoryCommand(categoryId, parentId));
        return ResponseEntity
                .accepted()
                .build();
    }

    @DeleteMapping(path = "/{parentId}")
    public ResponseEntity<Void> removeParentCategoryCommand(@PathVariable UUID categoryId, @PathVariable UUID parentId) {
        gateway.sendAsync(new RemoveParentCategoryCommand(categoryId, parentId));
        return ResponseEntity
                .accepted()
                .build();
    }
}
