package pl.app.learning.topic.query;


import pl.app.common.service.QueryService;
import pl.app.learning.topic.application.domain.Topic;

import java.util.UUID;

public interface TopicQueryService extends
        QueryService.Full<UUID, TopicQueryEntity> {
}
