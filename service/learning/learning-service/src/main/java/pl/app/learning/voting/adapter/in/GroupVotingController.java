package pl.app.learning.voting.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.app.common.cqrs.command.gateway.CommandGateway;
import pl.app.learning.voting.application.domain.DomainObjectType;
import pl.app.learning.voting.application.port.in.command.*;

import java.util.UUID;

@RestController
@RequestMapping(GroupVotingController.resourcePath)
@RequiredArgsConstructor
public class GroupVotingController {
    public static final String resourceName = "groups";
    public static final String resourcePath = "/api/v1/" + resourceName;

    private final CommandGateway gateway;

    @PostMapping("/{groupId}/likes/{userId}")
    public ResponseEntity<Void> createLike(@PathVariable UUID groupId, @PathVariable UUID userId) {
        var command = new AddUserLikeCommand(groupId, DomainObjectType.GROUP, userId);
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    @DeleteMapping("/{groupId}/likes/{userId}")
    public ResponseEntity<Void> deleteLike(@PathVariable UUID groupId, @PathVariable UUID userId) {
        var command = new RemoveUserLikeCommand(groupId, DomainObjectType.GROUP, userId);
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    @PostMapping("/{groupId}/dislikes/{userId}")
    public ResponseEntity<Void> createDislike(@PathVariable UUID groupId, @PathVariable UUID userId) {
        var command = new AddUserDislikeCommand(groupId, DomainObjectType.GROUP, userId);
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    @DeleteMapping("/{groupId}/dislikes/{userId}")
    public ResponseEntity<Void> deleteDislike(@PathVariable UUID groupId, @PathVariable UUID userId) {
        var command = new RemoveUserDislikeCommand(groupId, DomainObjectType.GROUP, userId);
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }
    @DeleteMapping("/{groupId}/likes-dislikes/{userId}")
    public ResponseEntity<Void> deleteLikeAndDislike(@PathVariable UUID groupId, @PathVariable UUID userId) {
        var command = new RemoveUserLikeAndDislikeCommand(groupId, DomainObjectType.GROUP, userId);
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }
}
