package pl.app.learning.path.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.ddd.AggregateId;
import pl.app.common.service.QueryService;
import pl.app.common.shared.dto.BaseDto;
import pl.app.learning.path.query.dto.PathDto;
import pl.app.learning.path.query.dto.SimplePathDto;
import pl.app.learning.path.query.model.PathQuery;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
public class PathQueryService implements
        QueryService.Full<UUID, PathQuery> {
    private final PathQueryRepository repository;
    private final PathQueryRepository specificationRepository;
    private final PathQueryMapper mapper;

    private final Map<String, Class<?>> supportedDtoClasses = new LinkedHashMap<>() {{
        put("PathDto", PathDto.class);
        put("SimplePathDto", SimplePathDto.class);
        put("BaseDto", BaseDto.class);
        put("AggregateId", AggregateId.class);
    }};
}
