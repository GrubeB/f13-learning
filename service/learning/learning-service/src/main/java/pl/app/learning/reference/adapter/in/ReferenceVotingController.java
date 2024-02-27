package pl.app.learning.reference.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.app.common.cqrs.command.gateway.CommandGateway;
import pl.app.learning.reference.application.port.in.command.AddUserDislikeCommand;
import pl.app.learning.reference.application.port.in.command.AddUserLikeCommand;
import pl.app.learning.reference.application.port.in.command.RemoveUserDislikeCommand;
import pl.app.learning.reference.application.port.in.command.RemoveUserLikeCommand;

import java.util.UUID;

@RestController
@RequestMapping(ReferenceVotingController.resourcePath)
@RequiredArgsConstructor
public class ReferenceVotingController {
    public static final String resourceName = "references";
    public static final String resourcePath = "/api/v1/" + resourceName;

    private final CommandGateway gateway;

    @PostMapping("/{referenceId}/likes")
    public ResponseEntity<Void> createLike(@PathVariable UUID referenceId, @RequestBody AddUserLikeCommand command) {
        command.setReferenceId(referenceId);
        gateway.sendAsync(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    @DeleteMapping("/{referenceId}/likes")
    public ResponseEntity<Void> deleteLike(@PathVariable UUID referenceId, @RequestBody RemoveUserLikeCommand command) {
        command.setReferenceId(referenceId);
        gateway.sendAsync(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    @PostMapping("/{referenceId}/dislikes")
    public ResponseEntity<Void> createDislike(@PathVariable UUID referenceId, @RequestBody AddUserDislikeCommand command) {
        command.setReferenceId(referenceId);
        gateway.sendAsync(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    @DeleteMapping("/{referenceId}/dislikes")
    public ResponseEntity<Void> deleteDislike(@PathVariable UUID referenceId, @RequestBody RemoveUserDislikeCommand command) {
        command.setReferenceId(referenceId);
        gateway.sendAsync(command);
        return ResponseEntity
                .accepted()
                .build();
    }
}
