package pl.app.learning.topic_snapshot.adapter.in;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.query_controller.QueryController;
import pl.app.learning.topic_snapshot.query.TopicSnapshotQueryService;
import pl.app.learning.topic_snapshot.query.model.TopicSnapshotQuery;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(TopicSnapshotByTopicQueryController.resourcePath)
@RequiredArgsConstructor
@Getter
public class TopicSnapshotByTopicQueryController implements
        QueryController.DtoFetchableWithFilter.Full<UUID, TopicSnapshotQuery> {

    public static final String resourceName = "snapshots";
    public static final String resourcePath = "/api/v1/topics/{topicId}/" + resourceName;
    public final TopicSnapshotQueryService service;
    private final Map<String, String> parentFilterMap = new LinkedHashMap<>() {{
        put("topicId", "snapshotOwnerId");
    }};
}
