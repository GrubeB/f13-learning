package pl.app.learning.reference.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.app.common.cqrs.command.gateway.CommandGateway;
import pl.app.learning.reference.application.port.in.command.*;
import pl.app.learning.reference.query.ReferenceQueryService;
import pl.app.learning.reference.query.dto.ReferenceDto;

import java.util.UUID;

@RestController
@RequestMapping(ReferenceVoitingController.resourcePath)
@RequiredArgsConstructor
public class ReferenceVoitingController {
    public static final String resourceName = "references";
    public static final String resourcePath = "/api/v1/" + resourceName;

    private final CommandGateway gateway;
    public static final String addUserLikePath = "/add-like";
    @PostMapping(path = addUserLikePath)
    public ResponseEntity<Void> handle(@RequestBody AddUserLikeCommand command) {
        gateway.sendAsync(command);
        return ResponseEntity
                .accepted()
                .build();
    }
    public static final String addUserDislikePath = "/add-dislike";
    @PostMapping(path = addUserDislikePath)
    public ResponseEntity<Void> handle(@RequestBody AddUserDislikeCommand command) {
        gateway.sendAsync(command);
        return ResponseEntity
                .accepted()
                .build();
    }

}
