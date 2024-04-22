package pl.app.learning.voting.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.app.common.cqrs.command.gateway.CommandGateway;
import pl.app.learning.voting.application.domain.DomainObjectType;
import pl.app.learning.voting.application.port.in.command.*;

import java.util.UUID;

@RestController
@RequestMapping(PathVotingController.resourcePath)
@RequiredArgsConstructor
public class PathVotingController {
    public static final String resourceName = "paths";
    public static final String resourcePath = "/api/v1/" + resourceName;

    private final CommandGateway gateway;

    @PostMapping("/{pathId}/likes/{userId}")
    public ResponseEntity<Void> createLike(@PathVariable UUID pathId, @PathVariable UUID userId) {
        var command = new AddUserLikeCommand(pathId, DomainObjectType.PATH, userId);
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    @DeleteMapping("/{pathId}/likes/{userId}")
    public ResponseEntity<Void> deleteLike(@PathVariable UUID pathId, @PathVariable UUID userId) {
        var command = new RemoveUserLikeCommand(pathId, DomainObjectType.PATH, userId);
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    @PostMapping("/{pathId}/dislikes/{userId}")
    public ResponseEntity<Void> createDislike(@PathVariable UUID pathId, @PathVariable UUID userId) {
        var command = new AddUserDislikeCommand(pathId, DomainObjectType.PATH, userId);
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    @DeleteMapping("/{pathId}/dislikes/{userId}")
    public ResponseEntity<Void> deleteDislike(@PathVariable UUID pathId, @PathVariable UUID userId) {
        var command = new RemoveUserDislikeCommand(pathId, DomainObjectType.PATH, userId);
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    @DeleteMapping("/{pathId}/likes-dislikes/{userId}")
    public ResponseEntity<Void> deleteLikeAndDislike(@PathVariable UUID pathId, @PathVariable UUID userId) {
        var command = new RemoveUserLikeAndDislikeCommand(pathId, DomainObjectType.PATH, userId);
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }
}
