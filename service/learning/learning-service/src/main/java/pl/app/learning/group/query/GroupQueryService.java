package pl.app.learning.group.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.ddd.AggregateId;
import pl.app.common.service.QueryService;
import pl.app.common.shared.dto.BaseDto;
import pl.app.learning.group.query.dto.GroupDto;
import pl.app.learning.group.query.dto.SimpleGroupDto;
import pl.app.learning.group.query.model.GroupQuery;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
public class GroupQueryService implements
        QueryService.Full<UUID, GroupQuery> {
    private final GroupQueryRepository repository;
    private final GroupQueryRepository specificationRepository;
    private final GroupQueryMapper mapper;

    private final Map<String, Class<?>> supportedDtoClasses = new LinkedHashMap<>() {{
        put("GroupDto", GroupDto.class);
        put("SimpleGroupDto", SimpleGroupDto.class);
        put("BaseDto", BaseDto.class);
        put("AggregateId", AggregateId.class);
    }};
}
