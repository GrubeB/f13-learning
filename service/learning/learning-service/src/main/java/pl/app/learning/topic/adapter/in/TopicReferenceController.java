package pl.app.learning.topic.adapter.in;

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
import pl.app.learning.topic.application.port.in.command.AddReferenceToTopicCommand;
import pl.app.learning.topic.application.port.in.command.RemoveReferenceFromTopicCommand;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(TopicReferenceController.resourcePath)
@RequiredArgsConstructor
public class TopicReferenceController {
    public static final String resourceName = "references";
    public static final String resourcePath = "/api/v1/topics/{topicId}/" + resourceName;

    private final ReferenceQueryService queryService;
    private final CommandGateway gateway;
    @PostMapping()
    public ResponseEntity<ReferenceDto> addReferenceToTopicCommand(@PathVariable UUID topicId,
                                                                   @RequestBody CreateReferenceCommand command) {
        UUID referenceId = gateway.send(command);
        gateway.send(new AddReferenceToTopicCommand(topicId, List.of(referenceId)));
        ReferenceDto dto = queryService.fetchById(referenceId, ReferenceDto.class);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dto);
    }

    @DeleteMapping("/{referenceId}")
    public ResponseEntity<Void> removeCategoryFromTopicCommand(@PathVariable UUID topicId,
                                                               @PathVariable UUID referenceId) {
        gateway.send(new RemoveReferenceFromTopicCommand(topicId, List.of(referenceId)));
        gateway.send(new DeleteReferenceCommand(referenceId));
        return ResponseEntity
                .accepted()
                .build();
    }
    @PutMapping(path = "/{referenceId}")
    public ResponseEntity<Void> updateCategoryFromTopicCommand(@PathVariable UUID referenceId,
                                                               @RequestBody UpdateReferenceCommand command) {
        command.setReferenceId(referenceId);
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }
}
