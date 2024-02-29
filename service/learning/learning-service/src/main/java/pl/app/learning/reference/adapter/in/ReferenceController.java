package pl.app.learning.reference.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.app.common.cqrs.command.gateway.CommandGateway;
import pl.app.learning.reference.application.port.in.command.CreateReferenceCommand;
import pl.app.learning.reference.application.port.in.command.DeleteReferenceCommand;
import pl.app.learning.reference.application.port.in.command.UpdateReferenceCommand;
import pl.app.learning.reference.query.ReferenceQueryService;
import pl.app.learning.reference.query.dto.ReferenceDto;

import java.util.UUID;

@RestController
@RequestMapping(ReferenceController.resourcePath)
@RequiredArgsConstructor
public class ReferenceController {
    public static final String resourceName = "references";
    public static final String resourcePath = "/api/v1/" + resourceName;

    private final CommandGateway gateway;
    private final ReferenceQueryService queryService;

    @PostMapping
    public ResponseEntity<ReferenceDto> handle(@RequestBody CreateReferenceCommand command) {
        UUID id = gateway.send(command);
        ReferenceDto dto = queryService.fetchById(id, ReferenceDto.class);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dto);
    }

    @DeleteMapping(path = "/{referenceId}")
    public ResponseEntity<Void> handle(@PathVariable UUID referenceId) {
        var command = new DeleteReferenceCommand(referenceId);
        gateway.sendAsync(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    @PutMapping(path = "/{referenceId}")
    public ResponseEntity<Void> handle(@PathVariable UUID referenceId, @RequestBody UpdateReferenceCommand command) {
        command.setReferenceId(referenceId);
        gateway.sendAsync(command);
        return ResponseEntity
                .accepted()
                .build();
    }

}
