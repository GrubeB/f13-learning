package pl.app.learning.group_revision.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.common.ddd.AggregateId;
import pl.app.common.service.QueryService;
import pl.app.common.shared.dto.BaseDto;
import pl.app.learning.group_revision.adapter.out.persistance.GroupRevisionRepository;
import pl.app.learning.group_revision.application.domain.GroupRevision;
import pl.app.learning.group_revision.query.dto.GroupRevisionDto;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Getter
public class GroupRevisionQueryService implements
        QueryService.Full<UUID, GroupRevision> {
    private final GroupRevisionRepository repository;
    private final GroupRevisionRepository specificationRepository;
    private final GroupRevisionQueryMapper mapper;

    private final Map<String, Class<?>> supportedDtoClasses = new LinkedHashMap<>() {{
        put("GroupRevisionDto", GroupRevisionDto.class);
        put("BaseDto", BaseDto.class);
        put("AggregateId", AggregateId.class);
    }};
}
