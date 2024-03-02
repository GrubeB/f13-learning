package pl.app.learning.topic_revision.adapter.in;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.query_controller.QueryController;
import pl.app.learning.topic_revision.application.domain.TopicRevision;
import pl.app.learning.topic_revision.query.TopicRevisionQueryService;

import java.util.UUID;

@RestController
@RequestMapping(TopicRevisionQueryController.resourcePath)
@RequiredArgsConstructor
@Getter
public class TopicRevisionQueryController implements
        QueryController.DtoFetchable.Full<UUID, TopicRevision> {
    public static final String resourceName = "topic-revisions";
    public static final String resourcePath = "/api/v1/" + resourceName;
    public final TopicRevisionQueryService service;
}
