package pl.app.learning.progress.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.ddd.AggregateId;
import pl.app.common.service.QueryService;
import pl.app.common.shared.dto.BaseDto;
import pl.app.learning.progress.query.dto.ProgressContainerDto;
import pl.app.learning.progress.query.model.ProgressContainerQuery;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
public class ProgressContainerQueryService implements
        QueryService.Full<UUID, ProgressContainerQuery> {
    private final ProgressContainerQueryRepository repository;
    private final ProgressContainerQueryRepository specificationRepository;
    private final ProgressContainerQueryMapper mapper;
    private final Map<String, Class<?>> supportedDtoClasses = new LinkedHashMap<>() {{
        put("ProgressContainerDto", ProgressContainerDto.class);
        put("BaseDto", BaseDto.class);
        put("AggregateId", AggregateId.class);
    }};
}
