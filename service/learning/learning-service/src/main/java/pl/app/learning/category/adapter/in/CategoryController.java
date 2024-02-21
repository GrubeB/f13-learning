package pl.app.learning.category.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.app.common.cqrs.command.gateway.CommandGateway;
import pl.app.learning.category.application.port.in.command.*;
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

    @DeleteMapping(path = "/{referenceId}")
    public ResponseEntity<Void> handle(@RequestBody DeleteCategoryCommand command) {
        gateway.sendAsync(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    @PutMapping(path = "/{referenceId}")
    public ResponseEntity<Void> handle(@RequestBody UpdateCategoryCommand command) {
        gateway.sendAsync(command);
        return ResponseEntity
                .accepted()
                .build();
    }
    public static final String changeCategoryStatusPath = "/change-status";

    @PostMapping(path = changeCategoryStatusPath)
    public ResponseEntity<Void> handle(@RequestBody ChangeStatusCategoryCommand command) {
        gateway.sendAsync(command);
        return ResponseEntity
                .accepted()
                .build();
    }
}
