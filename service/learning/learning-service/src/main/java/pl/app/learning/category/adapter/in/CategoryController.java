package pl.app.learning.category.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.app.common.cqrs.command.gateway.CommandGateway;
import pl.app.learning.category.application.domain.CategoryStatus;
import pl.app.learning.category.application.port.in.command.ChangeStatusCategoryCommand;
import pl.app.learning.category.application.port.in.command.CreateCategoryCommand;
import pl.app.learning.category.application.port.in.command.DeleteCategoryCommand;
import pl.app.learning.category.application.port.in.command.UpdateCategoryCommand;
import pl.app.learning.category.query.CategoryQueryService;
import pl.app.learning.category.query.dto.CategoryDto;

import java.util.UUID;

@RestController
@RequestMapping(CategoryController.resourcePath)
@RequiredArgsConstructor
public class CategoryController {
    public static final String resourceName = "categories";
    public static final String resourcePath = "/api/v1/" + resourceName;

    private final CommandGateway gateway;
    public final CategoryQueryService service;

    @PostMapping
    public ResponseEntity<CategoryDto> handle(@RequestBody CreateCategoryCommand command) {
        UUID id = gateway.send(command);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.fetchById(id, CategoryDto.class));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> handle(@PathVariable UUID categoryId) {
        var command = new DeleteCategoryCommand(categoryId);
        gateway.sendAsync(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<Void> handle(@PathVariable UUID categoryId, @RequestBody UpdateCategoryCommand command) {
        command.setCategoryId(categoryId);
        gateway.sendAsync(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    @PutMapping("/{categoryId}/status/{status}")
    public ResponseEntity<Void> handle(@PathVariable UUID categoryId, @PathVariable CategoryStatus status) {
        gateway.sendAsync(new ChangeStatusCategoryCommand(categoryId, status));
        return ResponseEntity
                .accepted()
                .build();
    }
}
