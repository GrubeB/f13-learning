package pl.app.learning.topic.adapter.in;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.query_controller.QueryController;
import pl.app.learning.topic.query.TopicQueryService;
import pl.app.learning.topic.query.model.TopicQuery;

import java.util.UUID;

@RestController
@RequestMapping(TopicQueryController.resourcePath)
@RequiredArgsConstructor
@Getter
public class TopicQueryController implements
        QueryController.DtoFetchable.Full<UUID, TopicQuery> {
    public static final String resourceName = "topics";
    public static final String resourcePath = "/api/v1/" + resourceName;
    public final TopicQueryService service;
}
