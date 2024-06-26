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
import pl.app.learning.voting.application.domain.DomainObjectType;

import java.util.UUID;

@RestController
@RequestMapping(GroupReferenceController.resourcePath)
@RequiredArgsConstructor
public class GroupReferenceController {
    public static final String resourceName = "references";
    public static final String resourcePath = "/api/v1/groups/{groupId}/" + resourceName;

    private final ReferenceQueryService queryService;
    private final CommandGateway gateway;

    @PostMapping()
    public ResponseEntity<ReferenceDto> create(@PathVariable UUID groupId, @RequestBody CreateReferenceCommand command) {
        command.setDomainObjectId(groupId);
        command.setDomainObjectType(DomainObjectType.GROUP);
        UUID referenceId = gateway.send(command);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(queryService.fetchById(referenceId, ReferenceDto.class));
    }

    @DeleteMapping("/{referenceId}")
    public ResponseEntity<Void> delete(@PathVariable UUID groupId, @PathVariable UUID referenceId) {
        gateway.send(new DeleteReferenceCommand(referenceId));
        return ResponseEntity
                .accepted()
                .build();
    }

    @PutMapping(path = "/{referenceId}")
    public ResponseEntity<Void> update(@PathVariable UUID referenceId, @RequestBody UpdateReferenceCommand command) {
        command.setReferenceId(referenceId);
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }
}
