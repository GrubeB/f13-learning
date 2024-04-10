package pl.app.learning.voting.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.cqrs.command.gateway.CommandGateway;
import pl.app.learning.voting.application.port.in.command.*;

@RestController
@RequestMapping(VotingCommandController.resourcePath)
@RequiredArgsConstructor
public class VotingCommandController {
    public static final String resourceName = "votings";
    public static final String resourcePath = "/api/v1/" + resourceName + "/commands";

    private final CommandGateway gateway;

    @PostMapping("/add-like")
    public ResponseEntity<Void> handle(@RequestBody AddUserLikeCommand command) {
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    @PostMapping("/remove-like")
    public ResponseEntity<Void> handle(@RequestBody RemoveUserLikeCommand command) {
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    @PostMapping("/add-dislike")
    public ResponseEntity<Void> handle(@RequestBody AddUserDislikeCommand command) {
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    @PostMapping("/remove-dislike")
    public ResponseEntity<Void> handle(@RequestBody RemoveUserDislikeCommand command) {
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }

    @PostMapping("/remove")
    public ResponseEntity<Void> handle(@RequestBody RemoveUserLikeAndDislikeCommand command) {
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }
}
