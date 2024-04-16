package pl.app.learning.progress.adapter.in;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.cqrs.command.gateway.CommandGateway;
import pl.app.learning.progress.application.port.in.SetUserProgressCommand;

@RestController
@RequestMapping(ProgressContainerCommandController.resourcePath)
@RequiredArgsConstructor
public class ProgressContainerCommandController {
    public static final String resourceName = "progresses";
    public static final String resourcePath = "/api/v1/" + resourceName + "/commands";

    private final CommandGateway gateway;

    @PostMapping("/set")
    public ResponseEntity<Void> handle(@RequestBody SetUserProgressCommand command) {
        gateway.send(command);
        return ResponseEntity
                .accepted()
                .build();
    }
}
