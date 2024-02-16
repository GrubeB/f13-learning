package pl.app.learning.topic.adapter.in;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.cqrs.command.gateway.CommandGateway;
import pl.app.common.ddd.AggregateId;
import pl.app.common.util.EntityLocationUriUtils;
import pl.app.learning.topic.application.domain.Topic;
import pl.app.learning.topic.application.port.in.command.CreateTopicCommand;
import pl.app.learning.topic.query.TopicQueryEntity;
import pl.app.learning.topic.query.TopicQueryService;

import java.util.UUID;

@RestController
@RequestMapping(AccommodationTypeController.resourcePath)
@RequiredArgsConstructor
public class AccommodationTypeController {
    public static final String resourceName = "topics";
    public static final String resourcePath = "/api/v1/" + resourceName;

    public static final String createPath = "";

    private final CommandGateway gateway;
    public final TopicQueryService service;

    @PostMapping(path = createPath)
    public ResponseEntity<TopicQueryEntity> createAccommodationType(@RequestBody CreateTopicCommand command, HttpServletRequest request) {
        AggregateId topicId = gateway.send(command);
        TopicQueryEntity topic = service.fetchById(topicId.getId());
        return ResponseEntity
                .created(EntityLocationUriUtils.createdEntityLocationURI(topicId, request.getRequestURI()))
                .body(topic);
    }
}
