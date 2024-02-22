package pl.app.learning.topic_revision.adapter.in;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.app.common.cqrs.command.gateway.CommandGateway;
import pl.app.common.util.EntityLocationUriUtils;
import pl.app.learning.topic.application.port.in.command.MergeRevisionToTopicCommand;
import pl.app.learning.topic_revision.application.port.in.command.CreateTopicRevisionCommand;
import pl.app.learning.topic_revision.application.port.in.command.DeleteTopicRevisionCommand;
import pl.app.learning.topic_revision.application.port.in.command.UpdateTopicRevisionCommand;
import pl.app.learning.topic_revision.query.TopicRevisionQueryService;
import pl.app.learning.topic_revision.query.dto.TopicRevisionDto;

import java.util.UUID;

@RestController
@RequestMapping(MergeTopicRevisionController.resourcePath)
@RequiredArgsConstructor
public class MergeTopicRevisionController {
    public static final String resourceName = "topic-revisions";
    public static final String resourcePath = "/api/v1/" + resourceName;

    private final CommandGateway gateway;
    public static final String mergeRevisionPath = "/merge-revision";

    @PostMapping(path = mergeRevisionPath)
    public ResponseEntity<Void> handle(@RequestBody MergeRevisionToTopicCommand command) {
        gateway.sendAsync(command);
        return ResponseEntity
                .accepted()
                .build();
    }
}
