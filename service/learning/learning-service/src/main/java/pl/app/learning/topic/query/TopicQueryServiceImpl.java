package pl.app.learning.topic.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.shared.dto.BaseDto;
import pl.app.learning.topic.adapter.out.persistance.TopicRepository;
import pl.app.learning.topic.query.dto.TopicDto;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
class TopicQueryServiceImpl implements TopicQueryService {
    private final TopicQueryEntityRepository repository;
    private final TopicQueryEntityRepository specificationRepository;
    private final TopicQueryMapper mapper;

    private final Map<String, Class<?>> supportedDtoClasses = new LinkedHashMap<>() {{
        put("TopicDto", TopicDto.class);
        put("BaseDto", BaseDto.class);
    }};
}
