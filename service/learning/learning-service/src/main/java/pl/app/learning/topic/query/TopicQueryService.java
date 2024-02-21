package pl.app.learning.topic.query;


import pl.app.common.service.QueryService;

import java.util.UUID;

public interface TopicQueryService extends
        QueryService.Full<UUID, TopicQuery> {
}
