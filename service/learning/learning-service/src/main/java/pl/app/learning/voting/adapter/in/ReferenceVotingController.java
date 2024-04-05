package pl.app.learning.voting.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.app.common.cqrs.command.gateway.CommandGateway;
import pl.app.learning.voting.application.domain.DomainObjectType;
import pl.app.learning.voting.application.port.in.command.*;

import java.util.UUID;

@RestController
@RequestMapping(ReferenceVotingController.resourcePath)
@RequiredArgsConstructor
public class ReferenceVotingController {
    public static final String resourceName = "references";
    public static final String resourcePath = "/api/v1/" + resourceName;

    private final CommandGateway gateway;

    @PostMapping("/{referenceId}/likes/{userId}")
    public ResponseEntity<Void> createLike(@PathVariable UUID referenceId, @PathVariable UUID userId) {
        var command = new AddUserLikeCommand(referenceId, DomainObjectType.REFERENCE, userId);
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    @DeleteMapping("/{referenceId}/likes/{userId}")
    public ResponseEntity<Void> deleteLike(@PathVariable UUID referenceId, @PathVariable UUID userId) {
        var command = new RemoveUserLikeCommand(referenceId, DomainObjectType.REFERENCE, userId);
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    @PostMapping("/{referenceId}/dislikes/{userId}")
    public ResponseEntity<Void> createDislike(@PathVariable UUID referenceId, @PathVariable UUID userId) {
        var command = new AddUserDislikeCommand(referenceId, DomainObjectType.REFERENCE, userId);
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    @DeleteMapping("/{referenceId}/dislikes/{userId}")
    public ResponseEntity<Void> deleteDislike(@PathVariable UUID referenceId, @PathVariable UUID userId) {
        var command = new RemoveUserDislikeCommand(referenceId, DomainObjectType.REFERENCE, userId);
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }
    @DeleteMapping("/{referenceId}/likes-dislikes/{userId}")
    public ResponseEntity<Void> deleteLikeAndDislike(@PathVariable UUID referenceId, @PathVariable UUID userId) {
        var command = new RemoveUserLikeAndDislikeCommand(referenceId, DomainObjectType.REFERENCE, userId);
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }
}
