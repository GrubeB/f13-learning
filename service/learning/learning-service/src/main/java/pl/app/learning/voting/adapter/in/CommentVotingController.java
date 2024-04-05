package pl.app.learning.voting.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.app.common.cqrs.command.gateway.CommandGateway;
import pl.app.learning.voting.application.domain.DomainObjectType;
import pl.app.learning.voting.application.port.in.command.*;

import java.util.UUID;

@RestController
@RequestMapping(CommentVotingController.resourcePath)
@RequiredArgsConstructor
public class CommentVotingController {
    public static final String resourceName = "comments";
    public static final String resourcePath = "/api/v1/" + resourceName;

    private final CommandGateway gateway;

    @PostMapping("/{commentId}/likes/{userId}")
    public ResponseEntity<Void> createLike(@PathVariable UUID commentId, @PathVariable UUID userId) {
        var command = new AddUserLikeCommand(commentId, DomainObjectType.COMMENT, userId);
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    @DeleteMapping("/{commentId}/likes/{userId}")
    public ResponseEntity<Void> deleteLike(@PathVariable UUID commentId, @PathVariable UUID userId) {
        var command = new RemoveUserLikeCommand(commentId, DomainObjectType.COMMENT, userId);
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    @PostMapping("/{commentId}/dislikes/{userId}")
    public ResponseEntity<Void> createDislike(@PathVariable UUID commentId, @PathVariable UUID userId) {
        var command = new AddUserDislikeCommand(commentId, DomainObjectType.COMMENT, userId);
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    @DeleteMapping("/{commentId}/dislikes/{userId}")
    public ResponseEntity<Void> deleteDislike(@PathVariable UUID commentId, @PathVariable UUID userId) {
        var command = new RemoveUserDislikeCommand(commentId, DomainObjectType.COMMENT, userId);
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }
    @DeleteMapping("/{commentId}/likes-dislikes/{userId}")
    public ResponseEntity<Void> deleteLikeAndDislike(@PathVariable UUID commentId, @PathVariable UUID userId) {
        var command = new RemoveUserLikeAndDislikeCommand(commentId, DomainObjectType.COMMENT, userId);
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }
}
