package pl.app.learning.category.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.cqrs.command.gateway.CommandGateway;
import pl.app.learning.category.application.port.in.command.*;
import pl.app.learning.category.query.CategoryQueryService;
import pl.app.learning.category.query.dto.CategoryDto;

import java.util.UUID;

@RestController
@RequestMapping(CategoryCommandController.resourcePath)
@RequiredArgsConstructor
public class CategoryCommandController {
    public static final String resourceName = "categories";
    public static final String resourcePath = "/api/v1/" + resourceName + "/commands";

    private final CommandGateway gateway;
    public final CategoryQueryService service;

    @PostMapping("/create")
    public ResponseEntity<CategoryDto> handle(@RequestBody CreateCategoryCommand command) {
        UUID id = gateway.send(command);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.fetchById(id, CategoryDto.class));
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> handle(@RequestBody DeleteCategoryCommand command) {
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    @PostMapping("/update")
    public ResponseEntity<Void> handle(@RequestBody UpdateCategoryCommand command) {
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    @PostMapping("/change-status")
    public ResponseEntity<Void> handle(@RequestBody ChangeStatusCategoryCommand command) {
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    // PARENT
    @PostMapping("/add-parent")
    public ResponseEntity<Void> handle(@RequestBody AddParentCategoryCommand command) {
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    @PostMapping("/remove-parent")
    public ResponseEntity<Void> handle(@RequestBody RemoveParentCategoryCommand command) {
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    // CHILD
    @PostMapping("/add-child")
    public ResponseEntity<Void> handle(@RequestBody AddChildCategoryCommand command) {
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    @PostMapping("/remove-child")
    public ResponseEntity<Void> handle(@RequestBody RemoveChildCategoryCommand command) {
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }
}
