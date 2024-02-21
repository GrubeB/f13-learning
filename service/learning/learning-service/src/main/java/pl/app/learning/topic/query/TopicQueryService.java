package pl.app.learning.topic.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.service.QueryService;
import pl.app.common.shared.dto.BaseDto;
import pl.app.learning.topic.query.dto.TopicDto;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
public class TopicQueryService implements
        QueryService.Full<UUID, TopicQuery> {
    private final TopicQueryRepository repository;
    private final TopicQueryRepository specificationRepository;
    private final TopicQueryMapper mapper;

    private final Map<String, Class<?>> supportedDtoClasses = new LinkedHashMap<>() {{
        put("TopicDto", TopicDto.class);
        put("BaseDto", BaseDto.class);
    }};
}
