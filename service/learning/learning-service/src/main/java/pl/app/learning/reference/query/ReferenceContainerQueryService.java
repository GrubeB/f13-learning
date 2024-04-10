package pl.app.learning.reference.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.ddd.AggregateId;
import pl.app.common.service.QueryService;
import pl.app.common.shared.dto.BaseDto;
import pl.app.learning.reference.query.dto.ReferenceContainerDto;
import pl.app.learning.reference.query.model.ReferenceContainerQuery;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
public class ReferenceContainerQueryService implements
        QueryService.Full<UUID, ReferenceContainerQuery> {
    private final ReferenceContainerQueryRepository repository;
    private final ReferenceContainerQueryRepository specificationRepository;
    private final ReferenceContainerQueryMapper mapper;

    private final Map<String, Class<?>> supportedDtoClasses = new LinkedHashMap<>() {{
        put("ReferenceContainerDto", ReferenceContainerDto.class);
        put("BaseDto", BaseDto.class);
        put("AggregateId", AggregateId.class);
    }};
}
