package pl.app.learning.progress.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.ddd.AggregateId;
import pl.app.common.service.QueryService;
import pl.app.common.shared.dto.BaseDto;
import pl.app.learning.progress.query.dto.ProgressDto;
import pl.app.learning.progress.query.model.ProgressQuery;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
public class ProgressQueryService implements
        QueryService.Full<UUID, ProgressQuery> {
    private final ProgressQueryRepository repository;
    private final ProgressQueryRepository specificationRepository;
    private final ProgressQueryMapper mapper;
    private final Map<String, Class<?>> supportedDtoClasses = new LinkedHashMap<>() {{
        put("ProgressDto", ProgressDto.class);
        put("BaseDto", BaseDto.class);
        put("AggregateId", AggregateId.class);
    }};
}
