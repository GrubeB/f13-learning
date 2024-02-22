package pl.app.learning.topic.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.ddd.AggregateId;
import pl.app.common.service.QueryService;
import pl.app.common.shared.dto.BaseDto;
import pl.app.learning.topic.query.dto.TopicSnapshotDto;
import pl.app.learning.topic.query.model.TopicSnapshotQuery;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
public class TopicSnapshotQueryService implements
        QueryService.Full<UUID, TopicSnapshotQuery> {
    private final TopicSnapshotQueryRepository repository;
    private final TopicSnapshotQueryRepository specificationRepository;
    private final TopicSnapshotQueryMapper mapper;

    private final Map<String, Class<?>> supportedDtoClasses = new LinkedHashMap<>() {{
        put("TopicSnapshotDto", TopicSnapshotDto.class);
        put("BaseDto", BaseDto.class);
        put("AggregateId", AggregateId.class);
    }};
}
