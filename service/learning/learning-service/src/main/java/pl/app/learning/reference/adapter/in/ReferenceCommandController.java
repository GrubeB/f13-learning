package pl.app.learning.reference.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.cqrs.command.gateway.CommandGateway;
import pl.app.learning.reference.application.port.in.command.CreateReferenceCommand;
import pl.app.learning.reference.application.port.in.command.DeleteReferenceCommand;
import pl.app.learning.reference.application.port.in.command.UpdateReferenceCommand;
import pl.app.learning.reference.query.ReferenceQueryService;
import pl.app.learning.reference.query.dto.ReferenceDto;

import java.util.UUID;

@RestController
@RequestMapping(ReferenceCommandController.resourcePath)
@RequiredArgsConstructor
public class ReferenceCommandController {
    public static final String resourceName = "references";
    public static final String resourcePath = "/api/v1/" + resourceName + "/commands";

    private final CommandGateway gateway;
    private final ReferenceQueryService queryService;

    @PostMapping("/create")
    public ResponseEntity<ReferenceDto> handle(@RequestBody CreateReferenceCommand command) {
        UUID id = gateway.send(command);
        ReferenceDto dto = queryService.fetchById(id, ReferenceDto.class);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dto);
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> handle(@RequestBody DeleteReferenceCommand command) {
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    @PostMapping("/update")
    public ResponseEntity<Void> handle(@RequestBody UpdateReferenceCommand command) {
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }
}
