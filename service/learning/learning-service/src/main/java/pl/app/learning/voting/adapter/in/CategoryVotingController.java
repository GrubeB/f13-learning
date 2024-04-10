package pl.app.learning.voting.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.app.common.cqrs.command.gateway.CommandGateway;
import pl.app.learning.voting.application.domain.DomainObjectType;
import pl.app.learning.voting.application.port.in.command.*;

import java.util.UUID;

@RestController
@RequestMapping(CategoryVotingController.resourcePath)
@RequiredArgsConstructor
public class CategoryVotingController {
    public static final String resourceName = "categories";
    public static final String resourcePath = "/api/v1/" + resourceName;

    private final CommandGateway gateway;

    @PostMapping("/{topicId}/likes/{userId}")
    public ResponseEntity<Void> createLike(@PathVariable UUID topicId, @PathVariable UUID userId) {
        var command = new AddUserLikeCommand(topicId, DomainObjectType.CATEGORY, userId);
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    @DeleteMapping("/{topicId}/likes/{userId}")
    public ResponseEntity<Void> deleteLike(@PathVariable UUID topicId, @PathVariable UUID userId) {
        var command = new RemoveUserLikeCommand(topicId, DomainObjectType.CATEGORY, userId);
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    @PostMapping("/{topicId}/dislikes/{userId}")
    public ResponseEntity<Void> createDislike(@PathVariable UUID topicId, @PathVariable UUID userId) {
        var command = new AddUserDislikeCommand(topicId, DomainObjectType.CATEGORY, userId);
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    @DeleteMapping("/{topicId}/dislikes/{userId}")
    public ResponseEntity<Void> deleteDislike(@PathVariable UUID topicId, @PathVariable UUID userId) {
        var command = new RemoveUserDislikeCommand(topicId, DomainObjectType.CATEGORY, userId);
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    @DeleteMapping("/{topicId}/likes-dislikes/{userId}")
    public ResponseEntity<Void> deleteLikeAndDislike(@PathVariable UUID topicId, @PathVariable UUID userId) {
        var command = new RemoveUserLikeAndDislikeCommand(topicId, DomainObjectType.CATEGORY, userId);
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }
}
