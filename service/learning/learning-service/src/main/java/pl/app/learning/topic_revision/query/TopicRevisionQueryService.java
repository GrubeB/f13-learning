package pl.app.learning.topic_revision.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.ddd.AggregateId;
import pl.app.common.service.QueryService;
import pl.app.common.shared.dto.BaseDto;
import pl.app.learning.topic_revision.adapter.out.persistance.TopicRevisionRepository;
import pl.app.learning.topic_revision.application.domain.TopicRevision;
import pl.app.learning.topic_revision.query.dto.TopicRevisionDto;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
public class TopicRevisionQueryService implements
        QueryService.Full<UUID, TopicRevision> {
    private final TopicRevisionRepository repository;
    private final TopicRevisionRepository specificationRepository;
    private final TopicRevisionQueryMapper mapper;

    private final Map<String, Class<?>> supportedDtoClasses = new LinkedHashMap<>() {{
        put("TopicRevisionDto", TopicRevisionDto.class);
        put("BaseDto", BaseDto.class);
        put("AggregateId", AggregateId.class);
    }};
}
